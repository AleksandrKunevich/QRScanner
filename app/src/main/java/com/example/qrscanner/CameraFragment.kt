package com.example.qrscanner

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.qrscanner.databinding.FragmentCameraBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

private const val CAMERA_REQUEST_CODE = 10000001
private const val GALLERY_REQUEST_CODE = 20000002

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private val btnCamera: Button by lazy { binding.btnCamera }
    private val btnGallery: Button by lazy { binding.btnGallery }
    private val btnImageView: ImageView by lazy { binding.imageView }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
//        val startForResult =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
//            { result: ActivityResult ->
//                if (result.resultCode == Activity.RESULT_OK) {
//                    result.data
//                }
//            }
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startForResult.launch(intent)
    }

    override fun onStart() {
        super.onStart()

        btnCamera.setOnClickListener {
            goCamera()
        }

        btnGallery.setOnClickListener {
            goGallery()
        }

        btnImageView.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(requireContext())
            pictureDialog.setTitle("Select Action")
            val pictureDialogItem = arrayOf(
                "Select photo from Gallery",
                "Capture photo from Camera"
            )
            pictureDialog.setItems(pictureDialogItem) { dialog, which ->
                when (which) {
                    0 -> goGallery()
                    1 -> goCamera()
                }
            }
            pictureDialog.show()
        }
    }

    private fun checkPermission() {

        val permissionLauncherStorage = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (!isGranted) {
                startActivity(Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS))
            }
        }

        val permissionLauncherCamera = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (!isGranted) {
                startActivity(Intent(Settings.ACTION_APPLICATION_SETTINGS))
            }
        }
        permissionLauncherStorage.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionLauncherCamera.launch(Manifest.permission.CAMERA)
    }

    private fun goGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun goCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    saveBitmapInStorage(bitmap, requireContext())
                    binding.imageView.setImageBitmap(bitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    binding.imageView.setImageURI(data?.data)
                }
            }
        }
    }

    private fun saveBitmapInStorage(bitmap: Bitmap, context: Context) {
        val filename = "QR_" + "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }
}
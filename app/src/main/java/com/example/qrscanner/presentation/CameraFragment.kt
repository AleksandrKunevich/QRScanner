package com.example.qrscanner.presentation

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
import androidx.navigation.fragment.findNavController
import com.example.qrscanner.DaggerApplication
import com.example.qrscanner.R
import com.example.qrscanner.databinding.FragmentCameraBinding
import com.example.qrscanner.domain.ElementViewModel
import com.example.qrscanner.storage.ElementEntity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Inject

private const val CAMERA_REQUEST_CODE = 10000001
private const val GALLERY_REQUEST_CODE = 20000002

class CameraFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var viewModel: ElementViewModel

    private lateinit var binding: FragmentCameraBinding
    private val btnCamera: Button by lazy { binding.btnCamera }
    private val btnGallery: Button by lazy { binding.btnGallery }
    private val imgView: ImageView by lazy { binding.imageView }
    private val btnRoom: Button by lazy { binding.btnRoom }

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

//        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) !=
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            startActivity(Intent(Settings.ACTION_APPLICATION_SETTINGS))
//        }

        checkPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Settings.ACTION_INTERNAL_STORAGE_SETTINGS
        )
        checkPermission(
            Manifest.permission.CAMERA,
            Settings.ACTION_APPLICATION_SETTINGS
        )
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

        imgView.setOnClickListener {
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

        btnRoom.setOnClickListener {
            findNavController().navigate(R.id.action_cameraFragment_to_roomFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    saveBitmapInStorage(bitmap, requireContext())
                    viewModel.inset(
                        ElementEntity(
                            UUID.randomUUID(),
                            bitmap,
                            Date(),
                            System.currentTimeMillis()
                        )
                    )
                    binding.imageView.setImageBitmap(bitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    binding.imageView.setImageURI(data?.data)
                }
            }
        }
    }

    private fun checkPermission(manifest: String, settings: String) {

        val permissionLauncherStorage = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (!isGranted) {
                startActivity(Intent(settings))
            }
        }
        permissionLauncherStorage.launch(manifest)
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
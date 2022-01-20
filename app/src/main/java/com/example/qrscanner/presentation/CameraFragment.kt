package com.example.qrscanner.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.qrscanner.R
import com.example.qrscanner.databinding.FragmentCameraBinding
import com.example.qrscanner.utils.SaveBitmap

private const val CAMERA_REQUEST_CODE = 10000001
private const val GALLERY_REQUEST_CODE = 20000002

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private val btnCamera: Button by lazy { binding.btnCamera }
    private val btnGallery: Button by lazy { binding.btnGallery }
    private val imgView: ImageView by lazy { binding.imageView }
    private val btnRoom: Button by lazy { binding.btnRoom }
    private val btnRealtime: Button by lazy { binding.btnRealtime }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        btnCamera.setOnClickListener {
            goCamera()
        }

        btnGallery.setOnClickListener {
            goGallery()
        }

        btnRealtime.setOnClickListener {
            findNavController().navigate(R.id.action_cameraFragment_to_realtimeScannerFragment)
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
                    SaveBitmap().saveBitmapInStorage(bitmap, requireContext())
                    binding.imageView.setImageBitmap(bitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    binding.imageView.setImageURI(data?.data)
                }
            }
        }
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
}

package com.example.qrscanner.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.example.qrscanner.DaggerApplication
import com.example.qrscanner.domain.ElementViewModel
import com.example.qrscanner.storage.ElementEntity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Inject

class SaveBitmap {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var viewModel: ElementViewModel

    fun saveBitmapInStorage(bitmap: Bitmap, context: Context) {
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

        viewModel.inset(
            ElementEntity(
                UUID.randomUUID(),
                bitmap,
                Date(),
                System.currentTimeMillis()
            )
        )

        Toast.makeText(context, "$filename", Toast.LENGTH_SHORT).show()
    }
}
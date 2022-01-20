package com.example.qrscanner.test

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

//private fun checkPermission(manifest: String, settings: String) {
//
//    val permissionLauncherStorage = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (!isGranted) {
//            startActivity(Intent(settings))
//        }
//    }
//    permissionLauncherStorage.launch(manifest)
//}


//        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) !=
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            startActivity(Intent(Settings.ACTION_APPLICATION_SETTINGS))
//        }

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

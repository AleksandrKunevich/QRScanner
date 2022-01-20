package com.example.qrscanner.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.qrscanner.R

private const val PERMISSIONS_REQUEST_CODE = 10
private val permissions =
    arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

class PermissionsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (hasPermissions(requireContext())) {
            findNavController().navigate(R.id.action_permissionsFragment_to_realtimeScannerFragment)
        } else {
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (hasPermissions(requireContext())) {
            findNavController().navigate(R.id.action_permissionsFragment_to_realtimeScannerFragment)
        } else {
            Toast.makeText(context, "Permission request denied", Toast.LENGTH_LONG).show()
            activity?.finish()
        }
    }

    private fun hasPermissions(context: Context): Boolean {
        permissions.forEach { manifest ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    manifest
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }
}
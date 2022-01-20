package com.example.qrscanner.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.SparseArray
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.fragment.app.Fragment
import com.example.qrscanner.R
import com.example.qrscanner.databinding.RealtimeScannerBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.File

private const val CODE_CAMERA = 10000001

class RealtimeScannerFragment : Fragment() {

    private val surfaceView: SurfaceView by lazy { binding.surfaceView }
    private val txtValue: TextView by lazy { binding.txtValue }
    private lateinit var binding: RealtimeScannerBinding
    private lateinit var detector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    private lateinit var photoFile: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RealtimeScannerBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermission(
            Manifest.permission.CAMERA,
            Settings.ACTION_APPLICATION_SETTINGS
        )

        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {

        }
    }

    override fun onStart() {
        super.onStart()
        setupControls()
        Toast.makeText(context, "!!!!!!!!!!!", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODE_CAMERA && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val processor = object : Detector.Processor<Barcode> {
        override fun release() {

        }

        override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
            if (detections != null && detections.detectedItems.isNotEmpty()) {
                val qrCode: SparseArray<Barcode> = detections.detectedItems
                val code = qrCode.valueAt(0)
                txtValue.text = code.displayValue
            } else {
                txtValue.text = resources.getString(R.string.scanning)
            }
        }
    }

    private val surfaceCallBack = object : SurfaceHolder.Callback {
        @SuppressLint("MissingPermission")
        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                cameraSource.start(holder)
            } catch (
                e: Exception
            ) {
                Toast.makeText(context, "$e", Toast.LENGTH_SHORT).show()
            }
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        }

        @SuppressLint("MissingPermission")
        override fun surfaceDestroyed(holder: SurfaceHolder) {
            cameraSource.stop()
        }

    }

    private fun setupControls() {
        detector = BarcodeDetector.Builder(context).build()
        cameraSource =
            CameraSource.Builder(context, detector).setAutoFocusEnabled(true).build()
        surfaceView.holder.addCallback(surfaceCallBack)
        detector.setProcessor(processor)
    }

    private fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            CODE_CAMERA
        )
    }
}
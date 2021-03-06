package com.example.qrscanner.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.SparseArray
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.core.util.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.qrscanner.DaggerApplication
import com.example.qrscanner.R
import com.example.qrscanner.databinding.RealtimeScannerBinding
import com.example.qrscanner.data.SaveBitmapImpl
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealtimeScannerFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var saveBitmap: SaveBitmapImpl

    private val surfaceView: SurfaceView by lazy { binding.surfaceView }
    private val btnTakePicture: Button by lazy { binding.btnTakePicture }
    private val btnRoom: Button by lazy { binding.btnRoom }
    private val btnCamera: Button by lazy { binding.btnCamera }
    private val txtValue: TextView by lazy { binding.txtValue }
    private lateinit var binding: RealtimeScannerBinding
    private lateinit var detector: BarcodeDetector
    private lateinit var cameraSource: CameraSource

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RealtimeScannerBinding.inflate(inflater, container, false)
        initCamera()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        btnTakePicture.setOnClickListener {
            takeImage()
        }
        btnRoom.setOnClickListener {
            findNavController().navigate(R.id.action_realtimeScannerFragment_to_roomFragment)
        }
        btnCamera.setOnClickListener {
            findNavController().navigate(R.id.action_realtimeScannerFragment_to_cameraFragment)
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
            cameraSource.start(holder)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

        }

        @SuppressLint("MissingPermission")
        override fun surfaceDestroyed(holder: SurfaceHolder) {
            cameraSource.stop()
        }
    }

    private fun initCamera() {
        detector = BarcodeDetector.Builder(context).build()
        cameraSource =
            CameraSource.Builder(context, detector).setAutoFocusEnabled(true).build()
        surfaceView.holder.addCallback(surfaceCallBack)
        detector.setProcessor(processor)
    }

    private fun takeImage() {
        CoroutineScope(Dispatchers.IO).launch {
            cameraSource.takePicture(null, { bytes ->
                val bitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                saveBitmap.saveBitmapInStorage(bitmap, requireContext())
            })
        }
    }
}
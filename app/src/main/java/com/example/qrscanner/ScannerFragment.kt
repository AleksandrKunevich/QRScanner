package com.example.qrscanner

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.qrscanner.databinding.FragmentScannerBinding
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

class ScannerFragment : Fragment() {

    private lateinit var binding: FragmentScannerBinding
    private lateinit var imageView: ImageView
    private lateinit var txtView: TextView
    private lateinit var btnScan: Button
    private lateinit var btnCamera: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        imageView = binding.imgView
        txtView = binding.txtContent
        btnScan = binding.btnScan
        btnCamera = binding.btnCamera

        val myBitmap = BitmapFactory.decodeResource(
            requireContext().resources,
            R.drawable.img
        );
        imageView.setImageBitmap(myBitmap);

        val barcodeDetector = BarcodeDetector.Builder(requireContext())
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        btnScan.setOnClickListener {
            if (!barcodeDetector.isOperational) {
                txtView.text = "Could not set up the detector!"
            }
            val frame = Frame.Builder().setBitmap(myBitmap).build();
            val barcodes: SparseArray<Barcode> = barcodeDetector.detect(frame);
            if (barcodes.isNotEmpty()) {
                val thisCode = barcodes.valueAt(0)
                txtView.text = thisCode?.displayValue ?: "Empty..."
            }
        }

        btnCamera.setOnClickListener {
            findNavController().navigate(R.id.action_scannerFragment_to_cameraFragment)
        }
    }
}
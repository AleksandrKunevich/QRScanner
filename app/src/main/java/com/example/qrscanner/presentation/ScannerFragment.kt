package com.example.qrscanner.presentation

import android.graphics.Bitmap
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
import com.example.qrscanner.DaggerApplication
import com.example.qrscanner.R
import com.example.qrscanner.databinding.FragmentScannerBinding
import com.example.qrscanner.domain.ElementViewModel
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import javax.inject.Inject

private const val POSITION_CODE = "POSITION_CODE"

class ScannerFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var viewModel: ElementViewModel

    private lateinit var binding: FragmentScannerBinding
    private lateinit var imageView: ImageView
    private lateinit var txtView: TextView
    private lateinit var btnScan: Button
    private lateinit var btnRoom: Button
    private lateinit var btnCamera: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScannerBinding.inflate(inflater, container, false)
        imageView = binding.imgScanner
        txtView = binding.txtContent
        btnScan = binding.btnScan
        btnCamera = binding.btnCamera
        btnRoom = binding.btnRoom
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAll()
        var myBitmap: Bitmap =
            BitmapFactory.decodeResource(requireContext().resources, R.drawable.img)
        viewModel.element.observe(this) { elementList ->
            arguments?.let {
                val position = it.getInt(POSITION_CODE)
                if (position != -1) {
                    if (elementList.size > position) {
                        myBitmap = elementList[position].bitmap
                        imageView.setImageBitmap(myBitmap)
                    }
                }
            }
        }
        imageView.setImageBitmap(myBitmap)

        val barcodeDetector: BarcodeDetector = BarcodeDetector.Builder(requireContext())
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        btnScan.setOnClickListener {
            if (barcodeDetector.isOperational) {
                txtView.text = resources.getString(R.string.empty)
            }
            val frame = Frame.Builder().setBitmap(myBitmap).build();
            val barcodes: SparseArray<Barcode> = barcodeDetector.detect(frame);
            if (barcodes.isNotEmpty()) {
                val thisCode: Barcode = barcodes.valueAt(0)
                txtView.text = thisCode.displayValue ?: resources.getString(R.string.empty_barcode)
            }
        }

        btnCamera.setOnClickListener {
            findNavController().navigate(R.id.action_scannerFragment_to_cameraFragment)
        }

        btnRoom.setOnClickListener {
            findNavController().navigate(R.id.action_scannerFragment_to_roomFragment)
        }
    }
}
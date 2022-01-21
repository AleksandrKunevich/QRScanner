package com.example.qrscanner.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrscanner.DaggerApplication
import com.example.qrscanner.R
import com.example.qrscanner.databinding.FragmentRoomBinding
import com.example.qrscanner.domain.ElementViewModel
import com.example.qrscanner.presentation.recycler.ElementAdapter
import com.example.qrscanner.presentation.recycler.ElementClickListener
import java.io.ByteArrayOutputStream
import javax.inject.Inject


private const val POSITION_CODE = "POSITION_CODE"

class RoomFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var viewModel: ElementViewModel

    private val adapterNews by lazy { ElementAdapter(onClickElement) }
    private lateinit var binding: FragmentRoomBinding
    private val btnRealtimeScan: Button by lazy { binding.btnRealtimeScan }

    private val onClickElement: ElementClickListener = object : ElementClickListener {
        override fun onItemClickListener(position: Int) {
            val bundle = bundleOf(POSITION_CODE to position)
            findNavController().navigate(R.id.action_roomFragment_to_scannerFragment, bundle)
        }

        override fun onImageClickListener(bitmap: Bitmap) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_STREAM, getImageUri(requireContext(), bitmap))
            intent.type = "image/*"
            startActivity(Intent.createChooser(intent, "Open image..."))
        }
    }

    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAll()
        initRecycler()
        initObserver()
        btnRealtimeScan.setOnClickListener {
            findNavController().navigate(R.id.action_roomFragment_to_realtimeScannerFragment)
        }
    }

    private fun initObserver() {
        viewModel.element.observe(this) { elementList ->
            adapterNews.submitList(elementList)
        }
    }

    private fun initRecycler() {
        binding.apply {
            recyclerElement.adapter = adapterNews
            recyclerElement.layoutManager = LinearLayoutManager(activity)
        }

    }
}
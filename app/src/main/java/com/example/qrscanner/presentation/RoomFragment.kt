package com.example.qrscanner.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images
import android.view.*
import android.widget.Button
import android.widget.Toast
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
import com.example.qrscanner.data.storage.ElementEntity
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

        override fun onImageDeleteClickListener(elementEntity: ElementEntity) {
            viewModel.delete(elementEntity)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuCamera -> {
                findNavController().navigate(R.id.action_roomFragment_to_cameraFragment)
                return true
            }
            R.id.menuScan -> {
                findNavController().navigate(R.id.action_roomFragment_to_scannerFragment)
                return true
            }
            R.id.menuRealtime -> {
                findNavController().navigate(R.id.action_roomFragment_to_realtimeScannerFragment)
                return true
            }
            R.id.menuRoom -> {
                val toast =
                    Toast.makeText(requireContext(), "You are in the Room!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}

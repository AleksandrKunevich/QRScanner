package com.example.qrscanner.presentation

import android.os.Bundle
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
import com.example.qrscanner.storage.ElementEntity
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
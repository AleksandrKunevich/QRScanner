package com.example.qrscanner.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrscanner.DaggerApplication
import com.example.qrscanner.databinding.FragmentRoomBinding
import com.example.qrscanner.domain.ElementViewModel
import com.example.qrscanner.presentation.recycler.ElementAdapter
import javax.inject.Inject

class RoomFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var viewModel: ElementViewModel

    private val adapterNews by lazy { ElementAdapter() }

    private lateinit var binding: FragmentRoomBinding
    private val btnLoad: Button by lazy { binding.btnLoad }

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
        btnLoad.setOnClickListener {

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
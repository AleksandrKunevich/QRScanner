package com.example.qrscanner.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.qrscanner.DaggerApplication
import com.example.qrscanner.databinding.FragmentRoomBinding
import com.example.qrscanner.domain.ElementViewModel
import javax.inject.Inject

class RoomFragment : Fragment() {

    init {
        DaggerApplication.appComponent?.inject(this)
    }

    @Inject
    lateinit var viewModel: ElementViewModel

    private lateinit var binding: FragmentRoomBinding
    private val btnLoad: Button by lazy { binding.btnLoad }
    private val imgRoomView: ImageView by lazy { binding.imgRoomView }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        btnLoad.setOnClickListener {
            initObserver()
        }
    }

    private fun initObserver() {
        viewModel.getAll()
        viewModel.element.observe(this) {
            imgRoomView.setImageBitmap(viewModel.element.value?.last()?.bitmap)
            Log.d("!!!!!", "onStart: ${viewModel.element.value?.size}")
        }
    }
}
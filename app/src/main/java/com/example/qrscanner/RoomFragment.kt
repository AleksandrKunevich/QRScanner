package com.example.qrscanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.qrscanner.databinding.FragmentRoomBinding

class RoomFragment : Fragment() {

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

        }
    }
}
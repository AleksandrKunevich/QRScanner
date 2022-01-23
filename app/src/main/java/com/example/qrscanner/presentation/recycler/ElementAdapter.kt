package com.example.qrscanner.presentation.recycler

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qrscanner.data.storage.ElementEntity
import com.example.qrscanner.domain.ElementQR

class ElementAdapter(private val listener: ElementClickListener) :
    RecyclerView.Adapter<ElementHolder>() {

    private var items: List<ElementQR> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementHolder {
        return ElementHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ElementHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<ElementQR>) {
        items = data
        notifyDataSetChanged()
    }
}
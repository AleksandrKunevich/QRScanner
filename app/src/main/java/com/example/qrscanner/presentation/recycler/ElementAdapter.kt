package com.example.qrscanner.presentation.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qrscanner.storage.ElementEntity

class ElementAdapter(private val listener: ElementClickListener) :
    RecyclerView.Adapter<ElementHolder>() {

    private var items: List<ElementEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementHolder {
        return ElementHolder.from(parent, listener)
    }

    override fun onBindViewHolder(holder: ElementHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(data: List<ElementEntity>) {
        items = data
        notifyDataSetChanged()
    }
}
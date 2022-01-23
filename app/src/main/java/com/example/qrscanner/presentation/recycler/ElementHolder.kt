package com.example.qrscanner.presentation.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qrscanner.R
import com.example.qrscanner.data.storage.ElementEntity
import com.example.qrscanner.domain.ElementQR

class ElementHolder private constructor(
    itemView: View,
    private val listener: ElementClickListener
) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun from(
            parentViewGroup: ViewGroup, listener: ElementClickListener
        ): ElementHolder {
            return ElementHolder(
                LayoutInflater
                    .from(parentViewGroup.context)
                    .inflate(R.layout.item_element, parentViewGroup, false),
                listener
            )
        }
    }

    private val image: ImageView by lazy { itemView.findViewById(R.id.imgItem) }
    private val txtCount: TextView by lazy { itemView.findViewById(R.id.txtCount) }
    private val txtDate: TextView by lazy { itemView.findViewById(R.id.txtDate) }
    private val txtTime: TextView by lazy { itemView.findViewById(R.id.txtTime) }
    private val imgDelete: ImageView by lazy { itemView.findViewById(R.id.imgDelete) }

    @SuppressLint("SetTextI18n")
    fun bindView(item: ElementQR) {
        image.setImageBitmap(item.bitmap)
        txtCount.text = "Go scan ${item.uid}"
        txtDate.text = item.date.toString()
        txtTime.text = item.time.toString()

        txtCount.setOnClickListener {
            listener.onItemClickListener(adapterPosition)
        }

        image.setOnClickListener {
            listener.onImageClickListener(item.bitmap)
        }

        imgDelete.setOnClickListener {
            listener.onImageDeleteClickListener(item)
        }
    }
}
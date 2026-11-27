package com.example.labactivity41

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlavorTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val flavorTitleText: TextView = itemView.findViewById(R.id.flavor_title_text)

    fun bind(flavorTitle: FlavorTitle) {
        flavorTitleText.text = when (flavorTitle.flavor) {
            Flavor.SAVORY -> "Savory"
            Flavor.SWEET -> "Sweet"
        }
    }
}

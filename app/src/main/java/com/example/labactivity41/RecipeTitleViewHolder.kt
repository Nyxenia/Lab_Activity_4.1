package com.example.labactivity41

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val recipeTitleText: TextView = itemView.findViewById(R.id.recipe_title_text)

    fun bind(recipe: Recipe, onRecipeClick: (Recipe) -> Unit) {
        recipeTitleText.text = recipe.title
        itemView.setOnClickListener {
            onRecipeClick(recipe)
        }
    }
}

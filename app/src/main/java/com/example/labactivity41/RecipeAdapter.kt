package com.example.labactivity41

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(
    private val onRecipeClick: (Recipe) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val savoryRecipes = mutableListOf<Recipe>()
    private val sweetRecipes = mutableListOf<Recipe>()
    private var displayItems = listOf<RecipeModel>()

    companion object {
        private const val TYPE_FLAVOR_TITLE = 0
        private const val TYPE_RECIPE_TITLE = 1
    }

    init {
        updateDisplayItems()
    }

    private fun updateDisplayItems() {
        val items = mutableListOf<RecipeModel>()
        items.add(RecipeModel.FlavorHeader(FlavorTitle(Flavor.SAVORY)))
        items.addAll(savoryRecipes.map { RecipeModel.RecipeItem(it) })
        items.add(RecipeModel.FlavorHeader(FlavorTitle(Flavor.SWEET)))
        items.addAll(sweetRecipes.map { RecipeModel.RecipeItem(it) })
        displayItems = items
    }

    fun addRecipe(recipe: Recipe) {
        when (recipe.flavor) {
            Flavor.SAVORY -> savoryRecipes.add(recipe)
            Flavor.SWEET -> sweetRecipes.add(recipe)
        }
        updateDisplayItems()
        notifyDataSetChanged()
    }

    fun removeRecipeAt(position: Int) {
        if (position in displayItems.indices) {
            val item = displayItems[position]
            if (item is RecipeModel.RecipeItem) {
                when (item.recipe.flavor) {
                    Flavor.SAVORY -> savoryRecipes.remove(item.recipe)
                    Flavor.SWEET -> sweetRecipes.remove(item.recipe)
                }
                updateDisplayItems()
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, displayItems.size - position)
            }
        }
    }

    fun isHeader(position: Int): Boolean {
        return (position in displayItems.indices) && (displayItems[position] is RecipeModel.FlavorHeader)
    }

    override fun getItemViewType(position: Int): Int {
        return when (displayItems[position]) {
            is RecipeModel.FlavorHeader -> TYPE_FLAVOR_TITLE
            is RecipeModel.RecipeItem -> TYPE_RECIPE_TITLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_FLAVOR_TITLE -> {
                val view = inflater.inflate(R.layout.item_flavor_title, parent, false)
                FlavorTitleViewHolder(view)
            }
            TYPE_RECIPE_TITLE -> {
                val view = inflater.inflate(R.layout.item_recipe_title, parent, false)
                RecipeTitleViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = displayItems[position]) {
            is RecipeModel.FlavorHeader -> (holder as FlavorTitleViewHolder).bind(item.flavorTitle)
            is RecipeModel.RecipeItem -> (holder as RecipeTitleViewHolder).bind(item.recipe, onRecipeClick)
        }
    }

    override fun getItemCount(): Int = displayItems.size
}

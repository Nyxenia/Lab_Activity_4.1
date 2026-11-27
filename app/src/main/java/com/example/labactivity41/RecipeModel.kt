package com.example.labactivity41

sealed class RecipeModel {
    data class FlavorHeader(val flavorTitle: FlavorTitle) : RecipeModel()
    data class RecipeItem(val recipe: Recipe) : RecipeModel()
}

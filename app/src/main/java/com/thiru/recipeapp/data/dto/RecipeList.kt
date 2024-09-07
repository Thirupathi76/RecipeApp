package com.thiru.recipeapp.data.dto

data class RecipeList(
    val number: Int,
    val offset: Int,
    val results: List<Recipe>?,
    val totalResults: Int
)
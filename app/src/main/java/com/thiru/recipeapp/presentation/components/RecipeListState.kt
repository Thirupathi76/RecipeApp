package com.thiru.recipeapp.presentation.components

import com.thiru.recipeapp.data.dto.Recipe

data class RecipeListState(
    val loading: Boolean = false,
    val recipeList: List<Recipe> = emptyList(),
    val errorMessage: String? = null
)
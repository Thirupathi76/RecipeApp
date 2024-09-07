package com.thiru.recipeapp.presentation.components

import com.thiru.recipeapp.data.dto.RecipeSummary

data class RecipeSummaryState(
    val loading: Boolean = false,
    val recipeSummary: RecipeSummary? = null,
    val errorMessage: String? = null
)

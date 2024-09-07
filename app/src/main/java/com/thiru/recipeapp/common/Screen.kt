package com.thiru.recipeapp.common

sealed class Screen(val route: String) {
    data object RecipeListScreen: Screen("recipe_list")
    data object RecipeSummaryScreen: Screen("recipe_summary")
}
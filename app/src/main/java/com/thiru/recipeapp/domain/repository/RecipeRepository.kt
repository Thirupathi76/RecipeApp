package com.thiru.recipeapp.domain.repository

import com.thiru.recipeapp.common.ResultState
import com.thiru.recipeapp.data.dto.RecipeList
import com.thiru.recipeapp.data.dto.RecipeSummary
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipes(number: Int): Flow<ResultState<RecipeList>>
    suspend fun searchRecipes(query: String): Flow<ResultState<RecipeList>>
    suspend fun recipeSummary(id: Int): Flow<ResultState<RecipeSummary>>
}
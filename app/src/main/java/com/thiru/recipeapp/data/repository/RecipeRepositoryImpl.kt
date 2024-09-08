package com.thiru.recipeapp.data.repository

import com.thiru.recipeapp.data.RecipeApi
import com.thiru.recipeapp.data.dto.RecipeList
import com.thiru.recipeapp.data.dto.RecipeSummary
import com.thiru.recipeapp.common.ResultState
import com.thiru.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeApi: RecipeApi
) : RecipeRepository {

    override suspend fun getRecipes(number: Int): Flow<ResultState<RecipeList>> {

        return flow {
            emit(ResultState.Loading(true))

            val recipeList = try {
                recipeApi.getRecipes(20)//todo check
            } catch (e: Exception) {
                emit(ResultState.Error(message = "Error fetching recipes ${e.message}"))
                return@flow
            }
            emit(ResultState.Success(recipeList))
        }
    }

    override suspend fun searchRecipes(query: String): Flow<ResultState<RecipeList>> = flow {
        try {
            emit(ResultState.Loading(true))
            val response = recipeApi.searchRecipes(query)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun recipeSummary(id: Int): Flow<ResultState<RecipeSummary>> = flow {
        try {
            emit(ResultState.Loading(true))
            val response = recipeApi.recipeSummary(id)
            emit(ResultState.Success(response))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "Unknown error occurred"))
        }
    }

}

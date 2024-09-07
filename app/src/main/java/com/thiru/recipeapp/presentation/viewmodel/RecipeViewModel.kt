package com.thiru.recipeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiru.recipeapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    init {
        getRecipeList()
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            recipeRepository.getRecipes(20).collectLatest {

            }
        }
    }

    fun searchRecipe(query: String) {
        viewModelScope.launch {
            recipeRepository.searchRecipes(query).collectLatest {

            }
        }
    }

    fun getRecipeSummary(id: Int) {
        viewModelScope.launch {
            recipeRepository.recipeSummary(id).collectLatest {

            }
        }
    }
}
package com.thiru.recipeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiru.recipeapp.common.ResultState
import com.thiru.recipeapp.data.dto.RecipeList
import com.thiru.recipeapp.domain.repository.RecipeRepository
import com.thiru.recipeapp.presentation.components.RecipeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private var _recipeList = MutableStateFlow<ResultState<RecipeList>>(ResultState.Loading())
    val recipeList = _recipeList.asStateFlow()

    init {
        getRecipeList()
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            recipeRepository.getRecipes(20).collectLatest { result ->
                _recipeList.value = result
            }
        }
    }

    fun searchRecipe(query: String) {
        viewModelScope.launch {
            recipeRepository.searchRecipes(query)
                .collect { result ->
                    _recipeList.value = result
                }
        }
    }
}
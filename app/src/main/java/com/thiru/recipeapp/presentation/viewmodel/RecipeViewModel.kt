package com.thiru.recipeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiru.recipeapp.common.ResultState
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

    private var _recipeList = MutableStateFlow(RecipeListState())
    val recipeList = _recipeList.asStateFlow()

    init {
        getRecipeList()
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            _recipeList.update {
                it.copy(loading = true)
            }
            recipeRepository.getRecipes(20).collectLatest { result ->
                when (result) {
                    is ResultState.Success -> {
                        _recipeList.update {
                            it.copy(recipeList = result.data?.results!!)//todo
                        }
                    }

                    is ResultState.Error -> {
                        _recipeList.update {
                            it.copy(errorMessage = result.message, loading = false)
                        }
                    }

                    is ResultState.Loading -> {
                        _recipeList.update {
                            it.copy(loading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    fun searchRecipe(query: String) {
        viewModelScope.launch {
            _recipeList.update {
                it.copy(loading = true)
            }

            recipeRepository.searchRecipes(query).collectLatest { result ->
                when(result) {
                    is ResultState.Success -> {
                        _recipeList.update {
                            it.copy(recipeList = result.data?.results!!)//todo
                        }
                    }
                    is ResultState.Error -> {
                        _recipeList.update {
                            it.copy(errorMessage = result.message, loading = false)
                        }
                    }
                    is ResultState.Loading -> {
                        _recipeList.update {
                            it.copy(loading = result.isLoading)
                        }
                    }
                }
            }
        }
    }
}
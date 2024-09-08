package com.thiru.recipeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiru.recipeapp.common.ResultState
import com.thiru.recipeapp.data.dto.RecipeList
import com.thiru.recipeapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private var _recipeList = MutableStateFlow<ResultState<RecipeList>>(ResultState.Loading())
    val recipeList = _recipeList.asStateFlow()

    private var _initialResult: RecipeList? = null

    private val queryFlow = MutableStateFlow("")
    private var isFirstLoad = true

    init {
        viewModelScope.launch {
            queryFlow
                .debounce(300L)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank() && isFirstLoad) {
                        isFirstLoad = false
                        getRecipeList()
                    } else if (query.isBlank()) {
                        _recipeList.value = ResultState.Success(_initialResult)
                    } else if (query.length >= 3){
                        searchRecipe(query)
                    }
                }
        }
    }

    private fun getRecipeList() {
        viewModelScope.launch {
            recipeRepository.getRecipes(20).collectLatest { result ->
                _initialResult = result.data
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

    fun onQueryChange(newQuery: String) {
        if (queryFlow.value != newQuery) {
            queryFlow.value = newQuery
        }
    }
}

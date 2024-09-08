package com.thiru.recipeapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiru.recipeapp.common.ResultState
import com.thiru.recipeapp.data.dto.RecipeSummary
import com.thiru.recipeapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSummaryViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("id")
    val imageUrl: String? = savedStateHandle.get<String>("imageUrl")
    private var _recipeSummary = MutableStateFlow<ResultState<RecipeSummary>>(ResultState.Loading())
    val recipeSummary = _recipeSummary.asStateFlow()

    init {
        getRecipeSummary(id ?: -1)
    }

    private fun getRecipeSummary(id: Int) {
        viewModelScope.launch {
            recipeRepository.recipeSummary(id)
                .collect { result ->
                    _recipeSummary.value = result
                }
        }
    }

}
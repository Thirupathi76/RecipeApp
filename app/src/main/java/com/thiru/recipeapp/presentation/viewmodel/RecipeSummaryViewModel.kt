package com.thiru.recipeapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiru.recipeapp.common.ResultState
import com.thiru.recipeapp.domain.repository.RecipeRepository
import com.thiru.recipeapp.presentation.components.RecipeSummaryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSummaryViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("id")
    private val imageUrl: String? = savedStateHandle.get<String>("imageUrl")//todo
    private var _recipeSummary = MutableStateFlow(RecipeSummaryState())
    val recipeSummary = _recipeSummary.asStateFlow()

    init {
        getRecipeSummary(id ?: -1)
    }

    private fun getRecipeSummary(id: Int) {
        viewModelScope.launch {
            _recipeSummary.update {
                it.copy(loading = true)
            }
            recipeRepository.recipeSummary(id).collectLatest { result ->
                when (result) {
                    is ResultState.Success -> {
                        _recipeSummary.update {
                            it.copy(
                                recipeSummary =
                                result.data?.toRecipeSummary(imageUrl = imageUrl)
                            )
                        }
                    }

                    is ResultState.Error -> {
                        _recipeSummary.update {
                            it.copy(errorMessage = result.message, loading = false)
                        }
                    }

                    is ResultState.Loading -> {
                        _recipeSummary.update {
                            it.copy(loading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

}
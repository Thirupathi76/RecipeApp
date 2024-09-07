package com.thiru.recipeapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.thiru.recipeapp.presentation.viewmodel.RecipeViewModel

@Composable
fun RecipeListScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel<RecipeViewModel>()
) {
    val recipeListState = viewModel.recipeList.collectAsState().value


    if (recipeListState.recipeList.isEmpty()) {
        Box(modifier = Modifier.fillMaxWidth())
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
        ) {
            items(recipeListState.recipeList.size) { index ->
                RecipeItem(
                    recipe = recipeListState.recipeList[index],
                    navController = navController
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}
package com.thiru.recipeapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
    val recipesList = recipeListState.data?.results ?: emptyList()

    var searchText by remember { mutableStateOf(TextFieldValue()) }
    var results by remember { mutableStateOf(emptyList<String>()) }

    Column {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = MaterialTheme.shapes.small
                    )
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .background(Color.White)
                        .fillMaxWidth(),
                ) {
                    TextField(
                        value = searchText,
                        onValueChange = { newValue ->
                            searchText = newValue
                            if (newValue.text.length >= 3) {
                                viewModel.searchRecipe(newValue.text)
//                            results = fetchResults(newValue.text)
                            } else {
                                results = emptyList()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .background(color = Color.White),

                        textStyle = TextStyle(color = Color.Black),
                        placeholder = { Text("Enter a Recipe") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        leadingIcon = {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        },
                        trailingIcon = {
                            if (searchText.text.isNotEmpty()) {
                                IconButton(onClick = { searchText = TextFieldValue() }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            cursorColor = Color.LightGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }

            if (results.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(top = 8.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        results.forEach { result ->
                            Text(
                                text = result,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        if (recipesList.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth())
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
            ) {
                items(recipesList.size) { index ->
                    RecipeItem(
                        recipe = recipesList[index],
                        navController = navController,
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

            }
        }
    }

}
package com.thiru.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thiru.recipeapp.common.Screen
import com.thiru.recipeapp.presentation.components.RecipeListScreen
import com.thiru.recipeapp.presentation.components.RecipeSummaryScreen
import com.thiru.recipeapp.ui.theme.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            RecipeAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RecipeListScreen.route
                    ) {
                        composable(
                            route = Screen.RecipeListScreen.route
                        ) {
                            RecipeListScreen(navController)
                        }
                        composable(
                            route = Screen.RecipeSummaryScreen.route + "/{id}/{imageUrl}",
                            arguments = listOf(
                                navArgument("id") { type = NavType.IntType },
                                navArgument("imageUrl") { type = NavType.StringType }
                            )
                        ) {
                            RecipeSummaryScreen()
                        }
                    }
                }
            }
        }
    }
}

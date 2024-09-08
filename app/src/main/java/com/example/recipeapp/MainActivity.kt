package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.ui.screens.RecipeDetailsScreen
import com.example.recipeapp.ui.screens.RecipeListScreen
import com.example.recipeapp.ui.theme.RecipeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeAppTheme {
                val navController = rememberNavController()
                val viewModel = RecipeViewModel(application = application) // Initialize ViewModel

                NavHost(navController = navController, startDestination = "recipeList") {
                    // Main recipe list screen
                    composable("recipeList") {
                        RecipeListScreen(viewModel = viewModel, navController = navController)
                    }

                    // Detailed recipe screen
                    composable("recipeDetails/{recipeTitle}") { backStackEntry ->
                        val recipeTitle = backStackEntry.arguments?.getString("recipeTitle")
                        val recipe = viewModel.recipeList.firstOrNull { it.title == recipeTitle }

                        recipe?.let {
                            // Pass both navController and viewModel to RecipeDetailsScreen
                            RecipeDetailsScreen(navController = navController, recipe = it, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
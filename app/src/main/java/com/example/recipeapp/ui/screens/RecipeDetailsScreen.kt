package com.example.recipeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipeapp.data.RecipeEntity
import com.example.recipeapp.RecipeViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(navController: NavController, recipe: RecipeEntity, viewModel: RecipeViewModel) {
    var isFavorite by remember { mutableStateOf(recipe.favorite) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top bar with Back button and Heart icon
        TopAppBar(
            title = { Text(text = "Recipe Details") },
            navigationIcon = {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back")
                }
            },
            actions = {
                // Heart icon to toggle favorite status
                IconButton(onClick = {
                    // Toggle favorite status and update the ViewModel
                    isFavorite = !isFavorite
                    recipe.favorite = isFavorite
                    viewModel.updateFavoriteStatus(recipe)
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = recipe.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Ingredients", style = MaterialTheme.typography.headlineMedium)
        Text(text = recipe.ingredients)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Instructions", style = MaterialTheme.typography.headlineMedium)
        Text(text = recipe.instructions)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Detailed Cooking Instructions", style = MaterialTheme.typography.headlineMedium)
        Text(text = recipe.detailedInstructions)
    }
}

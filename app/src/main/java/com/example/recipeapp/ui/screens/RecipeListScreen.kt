package com.example.recipeapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipeapp.RecipeViewModel
import com.example.recipeapp.data.RecipeEntity
import com.example.recipeapp.ui.components.RecipeCard

@OptIn(ExperimentalFoundationApi::class)  // This opt-in is necessary for LazyVerticalGrid
@Composable
fun RecipeListScreen(viewModel: RecipeViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredRecipes = remember { mutableStateListOf<RecipeEntity>() }
    var showFavoritesOnly by remember { mutableStateOf(false) }  // New state for filtering favorites

    // Filter recipes based on the search query and favorite status
    LaunchedEffect(searchQuery, showFavoritesOnly) {
        filteredRecipes.clear()
        val filtered = viewModel.recipeList.filter {
            it.title.contains(searchQuery, ignoreCase = true) && (!showFavoritesOnly || it.favorite)
        }
        filteredRecipes.addAll(filtered)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Search bar
        TextField(
            value = searchQuery,
            onValueChange = { query -> searchQuery = query },
            label = { Text("Search Recipes") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { /* Perform search */ })
        )

        // Filter toggle for showing only favorites
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Checkbox(
                checked = showFavoritesOnly,
                onCheckedChange = { showFavoritesOnly = it }
            )
            Text("Show Favorites Only")
        }

        // Recipe list in a grid layout
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredRecipes) { recipe ->
                RecipeCard(recipe = recipe, navController = navController)
            }
        }
    }
}
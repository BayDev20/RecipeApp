package com.example.recipeapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipeapp.data.RecipeEntity

@Composable
fun RecipeCard(recipe: RecipeEntity, navController: NavController) {
    var isExpanded by remember { mutableStateOf(false) }

    val cardHeight = if (isExpanded) Modifier.height(IntrinsicSize.Max) else Modifier.height(220.dp)

    Card(
        modifier = Modifier
            .width(180.dp)
            .then(cardHeight)
            .padding(vertical = 4.dp)
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(recipe.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))

            if (isExpanded) {
                Text("Ingredients: ${recipe.ingredients}")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Instructions: ${recipe.instructions}")
                Spacer(modifier = Modifier.height(8.dp))

                // "Cook it" button
                Button(
                    onClick = {
                        // Navigate to RecipeDetailsScreen with the recipe details
                        navController.navigate("recipeDetails/${recipe.title}")
                    }
                ) {
                    Text("Cook it")
                }
            } else {
                Text("Tap to view details", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
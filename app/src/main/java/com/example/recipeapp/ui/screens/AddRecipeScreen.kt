package com.example.recipeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.recipeapp.RecipeViewModel

@Composable
fun AddRecipeScreen(viewModel: RecipeViewModel, onSave: () -> Unit) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var ingredients by remember { mutableStateOf(TextFieldValue()) }
    var instructions by remember { mutableStateOf(TextFieldValue()) }
    var nutrition by remember { mutableStateOf(TextFieldValue()) }
    var detailedInstructions by remember { mutableStateOf(TextFieldValue()) }  // Added detailedInstructions field

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text("Ingredients") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("Instructions") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = nutrition,
            onValueChange = { nutrition = it },
            label = { Text("Nutritional Info") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = detailedInstructions,
            onValueChange = { detailedInstructions = it },
            label = { Text("Detailed Cooking Instructions") },  // Field for detailed instructions
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.addRecipe(
                title.text,
                ingredients.text,
                instructions.text,
                nutrition.text,
                detailedInstructions.text
            )
            onSave()
        }) {
            Text("Save Recipe")
        }
    }
}
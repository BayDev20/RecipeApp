package com.example.recipeapp

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeDatabase
import com.example.recipeapp.data.RecipeEntity
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDatabase.getDatabase(application).recipeDao()

    // A MutableStateList to hold the recipes reactively
    val recipeList = mutableStateListOf<RecipeEntity>()

    // Initialize the ViewModel by loading sample recipes
    init {
        loadSampleRecipes()
    }

    // Load sample recipes when the app starts
    private fun loadSampleRecipes() {
        viewModelScope.launch {
            try {
                val sampleRecipes = listOf(
                    RecipeEntity(
                        title = "Spaghetti Bolognese",
                        ingredients = "Spaghetti, ground beef, tomatoes, onions, garlic",
                        instructions = "Cook spaghetti. Brown the beef. Simmer with tomatoes.",
                        nutrition = "Calories: 500, Protein: 25g, Fat: 10g",
                        detailedInstructions = "Boil water for spaghetti, cook pasta for 8-10 minutes. In a pan, brown the beef, sauté onions and garlic, add tomatoes and let simmer for 20 minutes. Combine and serve.",
                        favorite = false
                    ),
                    RecipeEntity(
                        title = "Chicken Caesar Salad",
                        ingredients = "Chicken breast, romaine, Caesar dressing, croutons, Parmesan",
                        instructions = "Grill the chicken. Toss salad with dressing.",
                        nutrition = "Calories: 300, Protein: 30g, Fat: 15g",
                        detailedInstructions = "Grill the chicken until fully cooked. Chop romaine lettuce, toss with Caesar dressing, croutons, and shaved Parmesan. Slice the chicken and place it on top.",
                        favorite = false
                    ),
                    RecipeEntity(
                        title = "Pancakes",
                        ingredients = "Flour, eggs, milk, sugar, butter",
                        instructions = "Mix the ingredients. Fry on a pan.",
                        nutrition = "Calories: 350, Protein: 8g, Fat: 12g",
                        detailedInstructions = "In a bowl, whisk together flour, eggs, milk, and sugar until smooth. Melt butter in a frying pan. Pour batter and cook pancakes until golden brown on each side.",
                        favorite = false
                    ),
                    RecipeEntity(
                        title = "Grilled Cheese Sandwich",
                        ingredients = "Bread, cheese, butter",
                        instructions = "Butter the bread, add cheese, and grill.",
                        nutrition = "Calories: 400, Protein: 12g, Fat: 20g",
                        detailedInstructions = "Butter both sides of the bread slices. Place cheese between the slices and grill in a pan over medium heat until golden brown and cheese is melted.",
                        favorite = false
                    ),
                    RecipeEntity(
                        title = "Beef Tacos",
                        ingredients = "Ground beef, taco shells, lettuce, cheese, tomatoes",
                        instructions = "Cook beef. Assemble tacos with ingredients.",
                        nutrition = "Calories: 450, Protein: 28g, Fat: 18g",
                        detailedInstructions = "Cook the ground beef with taco seasoning. Warm taco shells. Assemble tacos with beef, shredded lettuce, diced tomatoes, and shredded cheese.",
                        favorite = false
                    ),
                    RecipeEntity(
                        title = "Chicken Alfredo",
                        ingredients = "Chicken breast, fettuccine, Alfredo sauce",
                        instructions = "Cook fettuccine. Grill chicken. Mix with Alfredo sauce.",
                        nutrition = "Calories: 600, Protein: 30g, Fat: 35g",
                        detailedInstructions = "Grill chicken breasts until fully cooked. Cook fettuccine pasta according to package instructions. Mix together with Alfredo sauce and top with sliced chicken.",
                        favorite = false
                    ),
                    RecipeEntity(
                        title = "Quinoa Salad",
                        ingredients = "Quinoa, cucumber, tomatoes, olive oil, lemon",
                        instructions = "Mix cooked quinoa with vegetables and dressing.",
                        nutrition = "Calories: 300, Protein: 8g, Fat: 10g",
                        detailedInstructions = "Cook quinoa according to package instructions. Mix with chopped cucumber, tomatoes, olive oil, and a squeeze of lemon juice. Serve cold or at room temperature.",
                        favorite = false
                    ),
                    RecipeEntity(
                        title = "BBQ Chicken",
                        ingredients = "Chicken drumsticks, BBQ sauce",
                        instructions = "Grill chicken with BBQ sauce.",
                        nutrition = "Calories: 500, Protein: 35g, Fat: 20g",
                        detailedInstructions = "Grill chicken drumsticks on a barbecue or grill. Baste with BBQ sauce during cooking until fully cooked and caramelized.",
                        favorite = false
                    ),
                    RecipeEntity(
                        title = "Tuna Salad",
                        ingredients = "Tuna, mayonnaise, celery, onions",
                        instructions = "Mix tuna with mayonnaise and vegetables.",
                        nutrition = "Calories: 250, Protein: 30g, Fat: 10g",
                        detailedInstructions = "Drain canned tuna. Mix with mayonnaise, diced celery, and onions. Serve in a sandwich, wrap, or over a bed of lettuce.",
                        favorite = false
                    )
                )
                recipeList.addAll(sampleRecipes)
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error loading sample recipes: ${e.message}")
            }
        }
    }

    // Search recipes based on a query
    fun searchRecipes(query: String) {
        viewModelScope.launch {
            try {
                val searchResult = recipeDao.searchRecipes("%$query%")
                recipeList.clear()
                recipeList.addAll(searchResult)
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error searching recipes: ${e.message}")
            }
        }
    }

    // Update the favorite status of a recipe
    fun updateFavoriteStatus(recipe: RecipeEntity) {
        viewModelScope.launch {
            try {
                recipeDao.updateRecipe(recipe)
                // Log the status update
                Log.d("RecipeViewModel", "Updated favorite status for recipe: ${recipe.title}")
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error updating favorite status: ${e.message}")
            }
        }
    }

    // Get only favorited recipes
    fun getFavoritedRecipes() {
        viewModelScope.launch {
            try {
                val favorites = recipeDao.getFavoritedRecipes()
                recipeList.clear()
                recipeList.addAll(favorites)
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching favorited recipes: ${e.message}")
            }
        }
    }

    // Add a new recipe
    fun addRecipe(
        title: String,
        ingredients: String,
        instructions: String,
        nutrition: String,
        detailedInstructions: String
    ) {
        viewModelScope.launch {
            try {
                val newRecipe = RecipeEntity(
                    title = title,
                    ingredients = ingredients,
                    instructions = instructions,
                    nutrition = nutrition,
                    detailedInstructions = detailedInstructions,
                    favorite = false  // Default to not favorited
                )
                recipeDao.insert(newRecipe)
                recipeList.add(newRecipe)
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error adding new recipe: ${e.message}")
            }
        }
    }
}
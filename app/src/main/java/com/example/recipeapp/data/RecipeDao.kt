package com.example.recipeapp.data

import androidx.room.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: RecipeEntity)

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE title LIKE :query")
    suspend fun searchRecipes(query: String): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE favorite = 1")
    suspend fun getFavoritedRecipes(): List<RecipeEntity>
}
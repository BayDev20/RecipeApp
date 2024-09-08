package com.example.recipeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val ingredients: String,
    val instructions: String,
    val nutrition: String,
    val detailedInstructions: String,
    var favorite: Boolean = false  // New field to track favorite status
)
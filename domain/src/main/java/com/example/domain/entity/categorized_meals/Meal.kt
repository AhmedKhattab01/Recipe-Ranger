package com.example.domain.entity.categorized_meals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
)
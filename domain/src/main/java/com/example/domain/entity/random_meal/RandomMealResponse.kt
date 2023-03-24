package com.example.domain.entity.random_meal

import com.example.domain.entity.meal.Meal

data class RandomMealResponse(
    val meals: List<Meal>
)
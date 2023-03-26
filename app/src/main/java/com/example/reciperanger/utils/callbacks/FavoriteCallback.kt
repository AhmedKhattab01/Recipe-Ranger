package com.example.reciperanger.utils.callbacks

import android.widget.ImageView
import com.example.domain.entity.categorized_meals.Meal

interface FavoriteCallback {
    fun favoriteClickListener(meal: Meal, imageView: ImageView)
}
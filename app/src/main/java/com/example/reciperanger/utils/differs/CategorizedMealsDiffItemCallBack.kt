package com.example.reciperanger.utils.differs

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.categorized_meals.Meal

class CategorizedMealsDiffItemCallBack : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }
}
package com.example.reciperanger.utils.differs

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.meal.Meal

class SearchDiffItemCallBack : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }
}
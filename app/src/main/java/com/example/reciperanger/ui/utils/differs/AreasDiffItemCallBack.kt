package com.example.reciperanger.ui.utils.differs

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.areas.Meal

class AreasDiffItemCallBack : DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.strArea == newItem.strArea
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }
}
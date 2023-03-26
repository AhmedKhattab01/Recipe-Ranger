package com.example.reciperanger.utils.differs

import androidx.recyclerview.widget.DiffUtil

class IngredientDiffItemCallBack : DiffUtil.ItemCallback<Map<String, String>>() {
    override fun areItemsTheSame(
        oldItem: Map<String, String>,
        newItem: Map<String, String>
    ): Boolean {
        return oldItem.keys == newItem.keys
    }

    override fun areContentsTheSame(
        oldItem: Map<String, String>,
        newItem: Map<String, String>
    ): Boolean {
        return oldItem == newItem
    }
}
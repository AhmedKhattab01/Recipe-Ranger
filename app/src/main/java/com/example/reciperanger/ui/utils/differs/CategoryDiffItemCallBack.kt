package com.example.reciperanger.ui.utils.differs

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.category.Category

class CategoryDiffItemCallBack : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}
package com.example.reciperanger.ui.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.category.Category
import com.example.reciperanger.databinding.ItemRvCategoryBinding
import com.example.reciperanger.ui.screens.home.HomeFragmentDirections
import com.example.reciperanger.ui.utils.differs.CategoryDiffItemCallBack

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffItemCallBack()) {
    inner class ViewHolder(val binding: ItemRvCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.category = item
            binding.executePendingBindings()

            Glide.with(binding.ivCategory)
                .load(item.strCategoryThumb)
                .into(binding.ivCategory)

            binding.root.setOnClickListener {
                it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategorizedMealsFragment(item.strCategory))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRvCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
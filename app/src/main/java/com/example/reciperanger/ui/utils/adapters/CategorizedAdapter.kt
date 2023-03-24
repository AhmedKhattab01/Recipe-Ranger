package com.example.reciperanger.ui.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.categorized_meals.Meal
import com.example.reciperanger.CategorizedMealsFragmentDirections
import com.example.reciperanger.databinding.ItemCategorizedMealsBinding
import com.example.reciperanger.ui.utils.differs.CategorizedMealsDiffItemCallBack

class CategorizedAdapter :
    ListAdapter<Meal, CategorizedAdapter.ViewHolder>(CategorizedMealsDiffItemCallBack()) {
    inner class ViewHolder(val binding: ItemCategorizedMealsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Meal) {
            binding.meal = item
            binding.executePendingBindings()

            binding.cvMeal.setOnClickListener {
                it.findNavController().navigate(CategorizedMealsFragmentDirections.actionCategorizedMealsFragmentToMealDetailsFragment(item.idMeal))
            }

            Glide.with(binding.ivMeal)
                .load(item.strMealThumb)
                .into(binding.ivMeal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCategorizedMealsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
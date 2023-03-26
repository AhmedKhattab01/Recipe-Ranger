package com.example.reciperanger.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.categorized_meals.Meal
import com.example.reciperanger.R
import com.example.reciperanger.databinding.ItemCategorizedMealsBinding
import com.example.reciperanger.screens.area_meals.AreaMealsFragmentDirections
import com.example.reciperanger.screens.categorized_meals.CategorizedMealsFragmentDirections
import com.example.reciperanger.screens.favorite.FavoriteFragmentDirections
import com.example.reciperanger.utils.callbacks.FavoriteCallback
import com.example.reciperanger.utils.differs.CategorizedMealsDiffItemCallBack

class MealsAdapter(private val favoriteCallback: FavoriteCallback) :
    ListAdapter<Meal, MealsAdapter.ViewHolder>(CategorizedMealsDiffItemCallBack()) {

    inner class ViewHolder(val binding: ItemCategorizedMealsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Meal) {
            binding.meal = item
            binding.executePendingBindings()

                binding.cvMeal.setOnClickListener {
                    if (it.findNavController().currentDestination?.id == R.id.areaMealsFragment) {
                        it.findNavController().navigate(
                            AreaMealsFragmentDirections.actionAreaMealsFragmentToMealDetailsFragment(
                                item.idMeal
                            )
                        )
                    }
                    else if (it.findNavController().currentDestination?.id == R.id.favoriteFragment) {
                        it.findNavController().navigate(
                            FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(
                                item.idMeal
                            )
                        )
                    }
                    else {
                        it.findNavController().navigate(
                            CategorizedMealsFragmentDirections.actionCategorizedMealsFragmentToMealDetailsFragment(
                                item.idMeal
                            )
                        )
                    }
                }

            binding.ivFavorite.setOnClickListener {
                favoriteCallback.favoriteClickListener(item, binding.ivFavorite)
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
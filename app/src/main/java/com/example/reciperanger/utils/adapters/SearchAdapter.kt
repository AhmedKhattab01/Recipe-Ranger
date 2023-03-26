package com.example.reciperanger.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.entity.meal.Meal
import com.example.reciperanger.databinding.ItemRvSearchBinding
import com.example.reciperanger.screens.search.SearchFragmentDirections
import com.example.reciperanger.utils.callbacks.FavoriteCallback
import com.example.reciperanger.utils.differs.SearchDiffItemCallBack

class SearchAdapter(private val favoriteCallback: FavoriteCallback) :
    ListAdapter<Meal, SearchAdapter.ViewHolder>(
        SearchDiffItemCallBack()
    ) {
    inner class ViewHolder(private val binding: ItemRvSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Meal) {
            binding.meal = item
            binding.executePendingBindings()
            Glide.with(binding.ivMeal).load(item.strMealThumb).into(binding.ivMeal)

            binding.cvMeal.setOnClickListener {
                it.findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToMealDetailsFragment(item.idMeal)
                )
            }

            binding.ivFavorite.setOnClickListener {
                favoriteCallback.favoriteClickListener(
                    com.example.domain.entity.categorized_meals.Meal(
                        item.idMeal,
                        item.strMeal,
                        item.strMealThumb
                    ), binding.ivFavorite
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRvSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
package com.example.reciperanger.screens.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.entity.categorized_meals.Meal
import com.example.reciperanger.databinding.FragmentFavoriteBinding
import com.example.reciperanger.utils.adapters.MealsAdapter
import com.example.reciperanger.utils.callbacks.FavoriteCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteCallback {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val adapter = MealsAdapter(this)
        binding.rvFavorite.adapter = adapter

        lifecycleScope.launchWhenCreated {
            favoriteViewModel.getAllFavoriteMeals().collect {
                adapter.submitList(it)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun favoriteClickListener(meal: Meal, imageView: ImageView) {
        lifecycleScope.launch(Dispatchers.IO) {
            favoriteViewModel.deleteFavorite(meal)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Removed from Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
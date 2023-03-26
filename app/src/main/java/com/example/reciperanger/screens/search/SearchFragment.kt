package com.example.reciperanger.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.entity.categorized_meals.Meal
import com.example.reciperanger.databinding.FragmentSearchBinding
import com.example.reciperanger.screens.favorite.FavoriteViewModel
import com.example.reciperanger.utils.adapters.SearchAdapter
import com.example.reciperanger.utils.callbacks.FavoriteCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SearchFragment : Fragment(), FavoriteCallback {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Initialize adapter
        val adapter = SearchAdapter(this)
        binding.rvSearch.adapter = adapter

        // Text on change listener to handle search
        binding.etSearch.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                searchViewModel.getMealsByCategoryFromNetwork(it.toString())
            }
        }

        lifecycleScope.launchWhenCreated {
            observeMeals(adapter)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private suspend fun observeMeals(adapter: SearchAdapter) {
        searchViewModel.meals.collect {
            // Check for response nullability then update ui
            if (it != null) {
                adapter.submitList(it.meals)
                binding.textView8.visibility = View.GONE
                binding.rvSearch.visibility = View.VISIBLE
            }
            else {
                binding.rvSearch.visibility = View.GONE
                binding.textView8.visibility = View.VISIBLE
            }
        }
    }

    override fun favoriteClickListener(meal: Meal, imageView: ImageView) {
        lifecycleScope.launch(Dispatchers.IO) {
            favoriteViewModel.insertFavorite(meal)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.example.reciperanger.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reciperanger.databinding.FragmentHomeBinding
import com.example.reciperanger.utils.adapters.AreasAdapter
import com.example.reciperanger.utils.adapters.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        showToolBar()

        // Initialize adapters
        val categoryAdapter = CategoryAdapter()
        binding.rvCategories.adapter = categoryAdapter

        val areasAdapter = AreasAdapter()
        binding.rvCountries.adapter = areasAdapter

        fetchDataFromNetwork()

        // Use coroutines to observe data changes and update UI accordingly
        lifecycleScope.launchWhenCreated {
            launch {
                observeCategory(categoryAdapter)
            }

            launch {
                observeRandomMeal()
            }

            launch {
                observeAreas(areasAdapter)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private suspend fun observeAreas(areasAdapter: AreasAdapter) {
        homeViewModel.areasResponse.collect { areasResponse ->
            // Remove Unknown areas from response then submit the filtered list
            areasResponse?.meals?.filter { areas ->
                areas.strArea != "Unknown"
            }?.also { filteredAreas ->
                areasAdapter.submitList(filteredAreas)
            }
        }
    }

    private suspend fun observeRandomMeal() {
        homeViewModel.randomMealResponse.collect { randomMeal ->
            if (randomMeal != null) {
                // Load random meal image
                Glide.with(binding.ivRandom)
                    .load(randomMeal.meals[0].strMealThumb)
                    .into(binding.ivRandom)

                binding.tvMealName.text = randomMeal.meals[0].strMeal

                binding.cardView.setOnClickListener {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(
                            randomMeal.meals[0].idMeal
                        )
                    )
                }
            }
        }
    }
    private suspend fun observeCategory(categoryAdapter: CategoryAdapter) {
        homeViewModel.categoryResponse.collect { categories ->
            if (categories != null) {
                categoryAdapter.submitList(categories.categories)
            }
        }
    }

    // Show tool bar in case its hidden
    private fun showToolBar() {
        (requireActivity() as AppCompatActivity).supportActionBar.let {
            if (it != null && !it.isShowing) {
                it.show()
            }
        }
    }

    // Fetch data from network using view model
    private fun fetchDataFromNetwork() {
        homeViewModel.getCategoryFromNetwork()
        homeViewModel.getRandomMealFromNetwork()
        homeViewModel.getAreasFromNetwork()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.example.reciperanger.ui.screens.home

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
import com.example.reciperanger.ui.utils.adapters.AreasAdapter
import com.example.reciperanger.ui.utils.adapters.CategoryAdapter
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

        (requireActivity() as AppCompatActivity).supportActionBar.let {
            if (it != null && !it.isShowing) {
                it.show()
            }
        }

        val categoryAdapter = CategoryAdapter()
        binding.rvCategories.adapter = categoryAdapter

        val areasAdapter = AreasAdapter()
        binding.rvCountries.adapter = areasAdapter

        homeViewModel.getCategoryFromNetwork()
        homeViewModel.getRandomMealFromNetwork()
        homeViewModel.getAreasFromNetwork()

        lifecycleScope.launchWhenCreated {
            launch {
                homeViewModel.categoryResponse.collect { categories ->
                    if (categories != null) {
                        categoryAdapter.submitList(categories.categories)
                    }
                }
            }

            launch {
                homeViewModel.randomMealResponse.collect { randomMeal ->
                    if (randomMeal != null) {
                        Glide.with(binding.ivRandom)
                            .load(randomMeal.meals[0].strMealThumb)
                            .into(binding.ivRandom)

                        binding.tvMealName.text = randomMeal.meals[0].strMeal

                        binding.cardView.setOnClickListener {
                            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(randomMeal.meals[0].idMeal))
                        }
                    }
                }
            }

            launch {
                homeViewModel.areasResponse.collect { areasResponse ->
                    areasResponse?.meals?.filter { areas ->
                        areas.strArea != "Unknown"
                    }?.also { filteredAreas ->
                        areasAdapter.submitList(filteredAreas)
                    }
                }
            }
        }



        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
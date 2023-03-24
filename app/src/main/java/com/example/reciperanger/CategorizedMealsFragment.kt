package com.example.reciperanger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.reciperanger.databinding.FragmentCategorizedMealsBinding
import com.example.reciperanger.ui.screens.categorized_meals.CategoryViewModel
import com.example.reciperanger.ui.utils.adapters.CategorizedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategorizedMealsFragment : Fragment() {

    private var _binding: FragmentCategorizedMealsBinding? = null
    private val binding get() = _binding!!

    private val categoryViewModel: CategoryViewModel by viewModels()

    private val args: CategorizedMealsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategorizedMealsBinding.inflate(inflater, container, false)

        val adapter = CategorizedAdapter()
        binding.rvCategorizedMeals.adapter = adapter

        categoryViewModel.getMealsByCategoryFromNetwork(args.categoryName)

        (requireActivity() as AppCompatActivity).apply {
            supportActionBar?.title = args.categoryName
        }

        lifecycleScope.launchWhenCreated {
            categoryViewModel.categorizedMeals.collect {
                Log.d("rabbit", "onCreateView: ${it?.meals}")
                if (it != null) {
                    adapter.submitList(it.meals)
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
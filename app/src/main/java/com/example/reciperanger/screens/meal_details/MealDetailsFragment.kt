package com.example.reciperanger.screens.meal_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.domain.entity.meal.Meal
import com.example.reciperanger.R
import com.example.reciperanger.databinding.FragmentMealDetailsBinding
import com.example.reciperanger.utils.adapters.IngredientAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailsFragment : Fragment() {

    private var _binding: FragmentMealDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MealDetailsFragmentArgs by navArgs()

    private val mealDetailsViewModel: MealDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMealDetailsBinding.inflate(inflater, container, false)

        // Fetch meal details from network
        mealDetailsViewModel.getMealDetailsFromNetwork(args.mealID)

        // Initialize adapter
        val adapter = IngredientAdapter()
        binding.rvIngredients.adapter = adapter


        binding.ivExpand.setOnClickListener {
            toggleExpansion()
        }



        lifecycleScope.launchWhenCreated {
            observeDetails(adapter)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private suspend fun observeDetails(
        adapter: IngredientAdapter
    ) {
        mealDetailsViewModel.mealResponse.collect {
            if (it != null) {
                with(it.meals[0]) {

                    val list = mapIngredients()

                    adapter.submitList(list)

                    // Set views values
                    binding.apply {
                        tvMealName.text = strMeal
                        tvCategory.text = "Category: $strCategory"
                        tvCountry.text = "Nationality: $strArea"
                        tvInstructions.text = strInstructions
                    }

                    // Launch tutorial URL
                    binding.btnWatch.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strYoutube))
                        startActivity(intent)
                    }

                    // Load thumb image
                    Glide.with(binding.ivThumb).load(strMealThumb).into(binding.ivThumb)
                }
            }
        }
    }

    private fun Meal.mapIngredients(): List<Map<String, String>> {
        // Extract ingredients and measures from the meal data
        val ingredientList = mutableListOf<String>()
        val measuresList = mutableListOf<String>()

        // Extract ingredients
        for (i in 1..20) {
            val ingredient = "strIngredient$i"

            if (!this::class.java.getDeclaredField(ingredient)
                    .apply { isAccessible = true }.get(this)
                    ?.toString()
                    ?.trim()
                    .isNullOrEmpty()
            ) {
                this::class.java.getDeclaredField(ingredient)
                    .apply { isAccessible = true }.get(this)
                    ?.let { it1 -> ingredientList.add(it1.toString()) }
            }
        }

        // Extract measures
        for (i in 1..20) {
            val measures = "strMeasure$i"

            if (!this::class.java.getDeclaredField(measures)
                    .apply { isAccessible = true }.get(this)
                    ?.toString()
                    ?.trim()
                    .isNullOrEmpty()
            ) {
                this::class.java.getDeclaredField(measures)
                    .apply { isAccessible = true }.get(this)
                    ?.let { it2 -> measuresList.add(it2.toString()) }
            }
        }

        // Map ingredients and measures
        val list = ingredientList.zip(measuresList).map { (ingredient, measure) ->
            mapOf("ingredient" to ingredient, "measure" to measure)
        }
        return list
    }

    // Toggle expansion for  Ingredient recycler view
    private fun toggleExpansion() {
        if (binding.rvIngredients.isShown) {
            binding.ivExpand.setImageResource(R.drawable.baseline_expand_more_24)
            binding.rvIngredients.visibility = View.GONE
        }
        else {
            binding.ivExpand.setImageResource(R.drawable.baseline_expand_less_24)
            binding.rvIngredients.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.example.reciperanger

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
import com.example.reciperanger.databinding.FragmentMealDetailsBinding
import com.example.reciperanger.ui.screens.meal_details.MealDetailViewModel
import com.example.reciperanger.ui.utils.adapters.IngredientAdapter
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

        mealDetailsViewModel.getAreasFromNetwork(args.mealID)

        val adapter = IngredientAdapter()
        binding.rvIngredients.adapter = adapter

        val ingredientList = mutableListOf<String>()
        val measuresList = mutableListOf<String>()

        //map.entries.toList().get(0).key
        lifecycleScope.launchWhenCreated {
            mealDetailsViewModel.mealResponse.collect {
                if (it != null) {
                    with(it.meals[0]) {

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

                        val list = ingredientList.zip(measuresList).map { (ingredient, measure) ->
                            mapOf("ingredient" to ingredient, "measure" to measure)
                        }

                        adapter.submitList(list)

                        binding.tvMealName.text = strMeal
                        binding.tvCategory.text = "Category: $strCategory"
                        binding.tvCountry.text = "Country: $strArea"
                        binding.tvInstructions.text = strInstructions

                        binding.btnWatch.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strYoutube))
                            startActivity(intent)
                        }



                        Glide.with(binding.ivThumb).load(strMealThumb).into(binding.ivThumb)
                    }
                }
            }
        }

        binding.ivExpand.setOnClickListener {
            if (binding.rvIngredients.isShown) {
                binding.ivExpand.setImageResource(R.drawable.baseline_expand_more_24)
                binding.rvIngredients.visibility = View.GONE
            }
            else {
                binding.ivExpand.setImageResource(R.drawable.baseline_expand_less_24)
                binding.rvIngredients.visibility = View.VISIBLE
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
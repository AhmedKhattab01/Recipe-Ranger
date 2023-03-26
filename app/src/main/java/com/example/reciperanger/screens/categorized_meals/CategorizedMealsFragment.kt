package com.example.reciperanger.screens.categorized_meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.domain.entity.categorized_meals.Meal
import com.example.reciperanger.databinding.FragmentCategorizedMealsBinding
import com.example.reciperanger.screens.favorite.FavoriteViewModel
import com.example.reciperanger.utils.adapters.MealsAdapter
import com.example.reciperanger.utils.callbacks.FavoriteCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CategorizedMealsFragment : Fragment(), FavoriteCallback {

    private var _binding: FragmentCategorizedMealsBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel : FavoriteViewModel by activityViewModels()

    private val categoryViewModel: CategoryViewModel by viewModels()

    private val args: CategorizedMealsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategorizedMealsBinding.inflate(inflater, container, false)

        val adapter = MealsAdapter(this)
        binding.rvCategorizedMeals.adapter = adapter

        // Fetch data from network
        categoryViewModel.getMealsByCategoryFromNetwork(args.categoryName)

        // Set action bar title to the category name
        (requireActivity() as AppCompatActivity).apply {
            supportActionBar?.title = args.categoryName
        }

        lifecycleScope.launchWhenCreated {
            categoryViewModel.categorizedMeals.collect {
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

    override fun favoriteClickListener(meal: Meal,imageView: ImageView) {
        lifecycleScope.launch(Dispatchers.IO) {
            favoriteViewModel.insertFavorite(meal)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.example.reciperanger.screens.area_meals

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
import com.example.reciperanger.databinding.FragmentAreaMealsBinding
import com.example.reciperanger.screens.favorite.FavoriteViewModel
import com.example.reciperanger.utils.adapters.MealsAdapter
import com.example.reciperanger.utils.callbacks.FavoriteCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AreaMealsFragment : Fragment(),FavoriteCallback {

    private var _binding: FragmentAreaMealsBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel : FavoriteViewModel by activityViewModels()

    private val adapter = MealsAdapter(this)
    private val areaViewModel: AreaViewModel by viewModels()

    private val args : AreaMealsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAreaMealsBinding.inflate(inflater, container, false)

        binding.rvAreaMeals.adapter = adapter

        areaViewModel.getMealsByAreaFromNetwork(args.area)

        (requireActivity() as AppCompatActivity).apply {
            supportActionBar?.title = args.area
        }

        lifecycleScope.launchWhenCreated {
            areaViewModel.areaMeals.collect {
                if (it != null) {
                    adapter.submitList(it.meals)
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
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
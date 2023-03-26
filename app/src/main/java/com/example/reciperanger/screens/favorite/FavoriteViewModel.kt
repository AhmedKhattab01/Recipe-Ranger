package com.example.reciperanger.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.categorized_meals.Meal
import com.example.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val localRepository: LocalRepository) : ViewModel() {

    fun getAllFavoriteMeals() : Flow<List<Meal>> = localRepository.getAllFavoriteMeals()

    fun insertFavorite(meal: Meal) = viewModelScope.launch(Dispatchers.IO) {
        localRepository.insertMeal(meal)
    }

    fun deleteFavorite(meal: Meal) = viewModelScope.launch(Dispatchers.IO) {
        localRepository.deleteMeal(meal)
    }
}
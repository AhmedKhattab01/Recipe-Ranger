package com.example.reciperanger.screens.area_meals

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.categorized_meals.CategorizedMealsResponse
import com.example.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AreaViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {
    private val _areaMeals: MutableStateFlow<CategorizedMealsResponse?> =
        MutableStateFlow(null)
    val areaMeals: StateFlow<CategorizedMealsResponse?> get() = _areaMeals

    fun getMealsByAreaFromNetwork(area: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = networkRepository.getMealsByAreaFromNetwork(area)

            if (data.isSuccessful) {
                _areaMeals.value = data.body()
            }
        } catch (e: java.lang.Exception) {
            Log.d("rabbit", "getCategoryFromNetwork: $e")
        }
    }
}
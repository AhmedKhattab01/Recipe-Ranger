package com.example.reciperanger.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.meal.MealResponse
import com.example.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val networkRepository: NetworkRepository): ViewModel() {
    private val _meals: MutableStateFlow<MealResponse?> =
        MutableStateFlow(null)
    val meals: StateFlow<MealResponse?> get() = _meals

    fun getMealsByCategoryFromNetwork(strSearch: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = networkRepository.getMealsBySearch(strSearch)

            if (data.isSuccessful) {
                _meals.value = data.body()
            }
        } catch (e: java.lang.Exception) {
            Log.d("rabbit", "getCategoryFromNetwork: $e")
        }
    }
}
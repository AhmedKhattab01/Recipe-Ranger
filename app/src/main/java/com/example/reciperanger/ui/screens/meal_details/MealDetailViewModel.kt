package com.example.reciperanger.ui.screens.meal_details

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
class MealDetailViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {

    private val _mealResponse: MutableStateFlow<MealResponse?> = MutableStateFlow(null)
    val mealResponse: StateFlow<MealResponse?> get() = _mealResponse

    fun getAreasFromNetwork(id : String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = networkRepository.getMealDetailsFromNetwork(id)
            if (data.isSuccessful) {
                _mealResponse.value = data.body()
                Log.d("rabbit", "getAreasFromNetwork: ${data.body()}")
            }
        } catch (e: java.lang.Exception) {
            Log.d("rabbit", "getCategoryFromNetwork: $e")
        }
    }
}
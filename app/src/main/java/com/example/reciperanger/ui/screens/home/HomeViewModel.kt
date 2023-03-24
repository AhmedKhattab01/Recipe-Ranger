package com.example.reciperanger.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.areas.AreasResponse
import com.example.domain.entity.category.CategoryResponse
import com.example.domain.entity.random_meal.RandomMealResponse
import com.example.domain.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val _categoryResponse: MutableStateFlow<CategoryResponse?> = MutableStateFlow(null)
    val categoryResponse: StateFlow<CategoryResponse?> get() = _categoryResponse

    private val _randomMealResponse: MutableStateFlow<RandomMealResponse?> = MutableStateFlow(null)
    val randomMealResponse: StateFlow<RandomMealResponse?> get() = _randomMealResponse

    private val _areasResponse: MutableStateFlow<AreasResponse?> = MutableStateFlow(null)
    val areasResponse: StateFlow<AreasResponse?> get() = _areasResponse

    fun getCategoryFromNetwork() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = networkRepository.getCategoryFromNetwork()
            if (data.isSuccessful) {
                _categoryResponse.value = data.body()
            }
        } catch (e: java.lang.Exception) {
            Log.d("rabbit", "getCategoryFromNetwork: $e")
        }
    }

    fun getRandomMealFromNetwork() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = networkRepository.getRandomMealFromNetwork()
            if (data.isSuccessful) {
                _randomMealResponse.value = data.body()
            }
        } catch (e: java.lang.Exception) {
            Log.d("rabbit", "getCategoryFromNetwork: $e")
        }
    }

    fun getAreasFromNetwork() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val data = networkRepository.getAreasFromNetwork()
            if (data.isSuccessful) {
                _areasResponse.value = data.body()
                Log.d("rabbit", "getAreasFromNetwork: ${data.body()}")
            }
        } catch (e: java.lang.Exception) {
            Log.d("rabbit", "getCategoryFromNetwork: $e")
        }
    }
}
package com.example.week5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week5.BuildConfig
import com.example.week5.data.model.WeatherResponse
import com.example.week5.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface WeatherUiState {
    object Initial : WeatherUiState
    object Loading : WeatherUiState
    data class Success(val weather: WeatherResponse) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Initial)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun getWeather(city: String) {
        if (city.isBlank()) return

        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            try {

                val response = RetrofitInstance.api.getWeather(city, BuildConfig.API_KEY)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = WeatherUiState.Success(response.body()!!)
                } else {
                    _uiState.value = WeatherUiState.Error("Virhe: ${response.code()} - Kaupunkia ei löydy.")
                }
            } catch (e: IOException) {
                _uiState.value = WeatherUiState.Error("Ei verkkoyhteyttä.")
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error("Virhe: ${e.localizedMessage}")
            }
        }
    }
}
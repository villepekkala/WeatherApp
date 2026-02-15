package com.example.week5.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5.viewmodel.WeatherViewModel
import com.example.week5.viewmodel.WeatherUiState

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var cityInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = cityInput,
            onValueChange = { cityInput = it },
            label = { Text("Syötä kaupunki") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.getWeather(cityInput) },
            enabled = cityInput.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hae sää")
        }

        Spacer(modifier = Modifier.height(32.dp))

        when (val state = uiState) {
            is WeatherUiState.Initial -> Text("Anna kaupunki ja hae.")
            is WeatherUiState.Loading -> CircularProgressIndicator()
            is WeatherUiState.Success -> {
                Text(text = state.weather.name, style = MaterialTheme.typography.headlineMedium)
                Text(text = "${state.weather.main.temp} °C", style = MaterialTheme.typography.displayMedium)
                if (state.weather.weather.isNotEmpty()) {

                    val desc = state.weather.weather[0].description
                    Text(
                        text = desc.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            is WeatherUiState.Error -> Text(text = state.message, color = MaterialTheme.colorScheme.error)
        }
    }
}
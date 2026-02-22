package com.example.week5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week5.data.local.WeatherDatabase
import com.example.week5.data.remote.RetrofitInstance
import com.example.week5.data.repository.repository
import com.example.week5.ui.theme.Week5Theme
import com.example.week5.view.WeatherScreen
import com.example.week5.viewmodel.WeatherViewModel
import com.example.week5.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val database = WeatherDatabase.getDatabase(applicationContext)
        val dao = database.weatherDao()


        val api = RetrofitInstance.api
        val repository = repository(api, dao)


        val viewModelFactory = WeatherViewModelFactory(repository)

        setContent {
            Week5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    WeatherScreen(viewModel = viewModel(factory = viewModelFactory))
                }
            }
        }
    }
}
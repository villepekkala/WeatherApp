package com.example.week5.data.repository

import android.util.Log
import com.example.week5.data.model.WeatherResponse
import com.example.week5.data.remote.WeatherApi
import com.example.week5.data.local.Entity
import com.example.week5.data.local.Dao
import kotlinx.coroutines.flow.Flow


class repository (
private val api: WeatherApi,
private val dao: Dao
){
    suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
        val currentTime = System.currentTimeMillis()
        val cacheExpiration = 30*1000*60

        val localData = dao.getWeather(city)
        if (localData != null && currentTime - localData.lastUpdates < cacheExpiration) {
        Log.d("WeatherRepo", "Data haettu roomista")
        return mapEntityToResponse(localData)
    }
Log.d("WeatherRepo", "Data haettu api:sta")
        val response = api.getWeather(city, apiKey)
if (response.isSuccessful && response.body() != null) {
    val weatherResponse = response.body()!!
   dao.insert(Entity(
       city = weatherResponse.name,
       temp = weatherResponse.main.temp,
       description = weatherResponse.weather[0].description,
       lastUpdates = System.currentTimeMillis()
   )
)
    return weatherResponse
} else {
    throw Exception("Virhe Haussa")
}
}
    private fun mapEntityToResponse(entity: Entity): WeatherResponse {
        return WeatherResponse(
            name = entity.city,
            main = com.example.week5.data.model.Main(temp = entity.temp),
            weather = listOf(com.example.week5.data.model.Weather(description = entity.description))
        )
    }
    fun getSearchHistory(): Flow<List<Entity>> {
        return dao.getAll()
    }}
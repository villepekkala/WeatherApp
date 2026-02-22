package com.example.week5.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Entity)
    @Query("SELECT * FROM weather_table WHERE city = :city")
    suspend fun getWeather(city: String): Entity?

    @Query ("SELECT * FROM weather_table ORDER BY lastUpdates DESC")
    fun getAll(): Flow<List<Entity>>

}
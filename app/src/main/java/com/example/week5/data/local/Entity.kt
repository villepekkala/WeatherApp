package com.example.week5.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")

data class Entity(
    @PrimaryKey val city: String,
    val temp: Double,
    val description: String,
    val lastUpdates: Long
)

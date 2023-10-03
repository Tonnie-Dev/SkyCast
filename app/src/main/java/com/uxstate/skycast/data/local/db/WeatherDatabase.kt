package com.uxstate.skycast.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uxstate.skycast.data.local.converter.Converters
import com.uxstate.skycast.data.local.dao.WeatherDao
import com.uxstate.skycast.data.local.db.WeatherDatabase.Companion.DATABASE_VERSION
import com.uxstate.skycast.data.local.db.WeatherDatabase.Companion.IS_EXPORT_SCHEMA
import com.uxstate.skycast.data.local.entity.CurrentEntity
import com.uxstate.skycast.data.local.entity.ForecastEntity

@Database(
        entities = [CurrentEntity::class, ForecastEntity::class],
        version = DATABASE_VERSION,
        exportSchema = IS_EXPORT_SCHEMA
)

@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val dao: WeatherDao

    companion object {

        const val DATABASE_NAME = "weather_database"
        const val IS_EXPORT_SCHEMA = false
        const val DATABASE_VERSION = 1
    }
}
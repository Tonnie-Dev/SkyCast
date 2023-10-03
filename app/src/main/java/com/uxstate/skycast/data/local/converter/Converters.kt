package com.uxstate.skycast.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Types
import com.uxstate.skycast.domain.model.WeatherDescription
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(private val jsonParser: JsonParser) {


    private val type = Types.newParameterizedType(List::class.java, WeatherDescription::class.java)

    @TypeConverter
    fun writeWeatherDescListToRoom(list: List<WeatherDescription>): String {

        return jsonParser.toJson(obj = list, type = type) ?: "[]"
    }

    @TypeConverter
    fun readWeatherDescListFromRoom(jsonString: String): List<WeatherDescription>? {

        return jsonParser.fromJson(json = jsonString, type = type) ?: emptyList()
    }
}
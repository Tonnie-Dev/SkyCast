package com.uxstate.skycast.data.local.converter

import java.lang.reflect.Type

interface JsonParser {
    //Write to Room - takes our object and return JSON String to store to room
    fun <T> toJson(obj: T, type: Type): String?
    //Read from Room - takes the actual JSON String and return an object of our type
    fun <T> fromJson(json: String, type: Type): T?


}
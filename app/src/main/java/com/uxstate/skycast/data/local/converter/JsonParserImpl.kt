package com.uxstate.skycast.data.local.converter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type
import javax.inject.Inject

class JsonParserImpl @Inject constructor(private val moshi: Moshi) : JsonParser {



    //Write Object to Room String
    override fun <T> toJson(obj: T, type: Type): String? {
        //use jsonAdapter<T> for generic adapter
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)

        return jsonAdapter.toJson(obj)
    }

    //Read Object from Room String
    override fun <T> fromJson(json: String, type: Type): T? {
        //use jsonAdapter<T> for generic adapter
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)
        return jsonAdapter.fromJson(json)
    }
}
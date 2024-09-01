package com.uxstate.skycast.data.remote.dto.forecast

import com.squareup.moshi.Json

data class Clouds(
    @Json(name = "all")
    val all: Int,
)

package com.uxstate.skycast.data.remote.dto.current

import com.squareup.moshi.Json

data class CurrentWindDto(
    @Json(name = "deg")
    val deg: Int,
    @Json(name = "speed")
    val speed: Double,
)

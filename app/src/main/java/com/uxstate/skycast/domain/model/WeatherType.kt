package com.uxstate.skycast.domain.model

import androidx.annotation.DrawableRes
import com.uxstate.skycast.R

sealed class WeatherType(val desc:String, @DrawableRes val icon:Int){

    // TODO: Revisit Overcast
    data object ClearSky:WeatherType(desc = "Clear Sky", icon = R.drawable.ic_clear_sky)
    data object MainlyClear:WeatherType(desc = "Mainly Clear", icon = R.drawable.ic_clear_sky)
    data object PartlyCloudy:WeatherType(desc = "Partly Cloudy", icon = R.drawable.ic_cloudy)
    data object Overcast:WeatherType(desc = "Overcast", icon = R.drawable.ic_cloudy)
    data object Foggy:WeatherType(desc = "Foggy", icon = R.drawable.ic_cloudy)
    data object DepositingRimeFog:WeatherType(desc = "Depositing Rime Fog", icon = R.drawable.ic_cloudy)
    data object LightDrizzle:WeatherType(desc = "Light Drizzle", icon = R.drawable.ic_rainshower)
    data object ModerateDrizzle:WeatherType(desc ="Moderate Drizzle", icon = R.drawable.ic_rainshower)
    data object DenseDrizzle:WeatherType(desc ="Dense Drizzle", icon = R.drawable.ic_rainshower)
    data object LightFreezingDrizzle:WeatherType(desc ="Slight Freezing Drizzle", icon = R.drawable.ic_snowyrainy)
    data object DenseFreezingDrizzle:WeatherType(desc ="Dense Freezing Drizzle", icon = R.drawable.ic_snowyrainy)
    data object SlightRain:WeatherType(desc ="Slight Rain", icon = R.drawable.ic_rainshower)
    data object ModerateRain:WeatherType(desc ="Rainy", icon = R.drawable.ic_rainshower)
    data object HeavyRain:WeatherType(desc ="Heavy Rainy", icon = R.drawable.ic_rainshower)
    data object  HeavyFreezingRain:WeatherType(desc ="Heavy Freezing Rain", icon = R.drawable.ic_snowyrainy)
    data object  SlightSnowFall:WeatherType(desc ="Slight Snowfall", icon = R.drawable.ic_snowy)
    data object  ModerateSnowFall:WeatherType(desc ="Moderate Snow Fall", icon = R.drawable.ic_snowy)
    data object  HeavySnowFall:WeatherType(desc ="Heavy Snow Fall", icon = R.drawable.ic_heavy_snow)
    data object  SnowGrains:WeatherType(desc ="Snow Grains", icon = R.drawable.ic_heavy_snow)
    data object  SlightRainShowers :WeatherType(desc ="Slight Rain Showers", icon = R.drawable.ic_rainshower)
    data object  ModerateRainShowers  :WeatherType(desc ="Moderate Rain Showers", icon = R.drawable.ic_rainshower)
    data object  ViolentRainShowers :WeatherType(desc ="Violent Rain Showers", icon = R.drawable.ic_rainshower)
    data object  SlightSnowShowers :WeatherType(desc ="Light Snow Showers", icon = R.drawable.ic_snowy)
    data object  HeavySnowShowers  :WeatherType(desc ="Heavy Snow Showers", icon = R.drawable.ic_snowy)
    data object  ModerateThunderstorm  :WeatherType(desc ="Moderate Thunderstorm", icon = R.drawable.ic_storm)
    data object  SlightHailThunderstorm  :WeatherType(desc ="Thunderstorm With Slight Hail", icon = R.drawable.ic_storm)
    data object  HeavyHailThunderstorm  :WeatherType(desc ="Thunderstorm With Heavy Hail", icon = R.drawable.ic_storm)


    companion object {

        fun fromWMO(iconCode:String):WeatherType {

            return when {
                iconCode.contains("01") -> ClearSky
                iconCode.contains("02") -> PartlyCloudy
                iconCode.contains("03") -> PartlyCloudy
                iconCode.contains("04") -> PartlyCloudy
                iconCode.contains("09") -> LightDrizzle
                iconCode.contains("10") -> LightDrizzle
                iconCode.contains("11") -> HeavyHailThunderstorm
                iconCode.contains("13") -> SnowGrains
                iconCode.contains("50") -> PartlyCloudy
                else -> {
                    MainlyClear
                }

            }
        }
    }
}


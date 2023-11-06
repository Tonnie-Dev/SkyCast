package com.uxstate.skycast.presentation.home

import com.uxstate.skycast.domain.connectivity.ConnectivityObserver
import com.uxstate.skycast.domain.model.CurrentWeather
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme

data class HomeState(
    val currentWeather: CurrentWeather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val geoPoint: GeoPoint? = null,
    val isLocationNull: Boolean = false,
    val isShowDialog: Boolean = true,
    val isShowBottomButtons: Boolean = false,
    val isShowSnackBar: Boolean = false,
    val netWorkStatus:ConnectivityObserver.Status = ConnectivityObserver.Status.UNAVAILABLE,
    val appPreferences: AppPreferences = appPreferencesInitialState
) {

    //serves to initialize app preferences initial state
    companion object {
        val appPreferencesInitialState =
            AppPreferences(
                    theme = Theme.SYSTEM,
                    tempUnit = TempUnit.CELSIUS,
                    savedCityId = -1
            )
    }


}

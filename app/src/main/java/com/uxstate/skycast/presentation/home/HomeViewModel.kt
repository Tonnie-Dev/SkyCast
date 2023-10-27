package com.uxstate.skycast.presentation.home

import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.uxstate.skycast.domain.location.LocationTracker
import com.uxstate.skycast.domain.model.GeoPoint
import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.repository.WeatherRepository
import com.uxstate.skycast.presentation.main.MainActivity
import com.uxstate.skycast.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val tracker: LocationTracker,
    private val locationManager: LocationManager,
    private val prefs: DataStoreOperations
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    val isLocationEnabled= MutableStateFlow(false)

    init {
        observePrefsFlow()

        updateLocationServiceStatus()
    }

    private fun getLastLocation() {


        viewModelScope.launch {


            when(val locData = tracker.getCurrentLocation()){

                is Resource.Success -> {
                    tracker.getCurrentLocation().data?.let {

                        geoPoint ->

                        _state.update {
                            it.copy(
                                    geoPoint = GeoPoint(
                                            latitude = geoPoint.latitude,
                                            geoPoint.longitude
                                    )
                            )
                        }
                        getCurrentWeather(geoPoint)
                    } ?: run {


                        _state.update { it.copy(errorMessage = "Error getting location", isLocationNull = true) }

                    }

                }
                is Resource.Error -> {

                    _state.update { it.copy(isLocationNull = true) }


                }
                else -> Unit
            }



        }


    }

    private fun getCurrentWeather(geoPoint: GeoPoint = _state.value.geoPoint) {


        repository.getCurrentWeather(geoPoint)
                .onEach {


                    result ->

                    when (result) {

                        is Resource.Error -> {

                            _state.update { it.copy(errorMessage = result.errorMessage) }
                        }

                        is Resource.Loading -> {


                            _state.update { it.copy(isLoading = result.isLoading) }

                        }

                        is Resource.Success -> {


                            result.data?.let { currentWeather ->

                                saveCityId(currentWeather.cityId)
                             _state.update { it.copy(currentWeather = currentWeather) }
                            }
                        }
                    }

                }
                .launchIn(viewModelScope)

    }


    private fun saveCityId(cityId: Int) {

        viewModelScope.launch {
            prefs.updateCityId(cityId = cityId)
        }
    }


    private fun observePrefsFlow() {

        viewModelScope.launch {


            prefs.appPreferences.collectLatest {

                appPrefs ->


                _state.update {
                    it.copy(
                            appPreferences = AppPreferences(
                                    tempUnit = appPrefs.tempUnit,
                                    theme = appPrefs.theme,
                                    savedCityId = appPrefs.savedCityId
                            )
                    )
                }
            }
        }
    }
    fun refreshWeather() {
        getCurrentWeather()

    }


    private fun updateLocationServiceStatus() {


        isLocationEnabled.value = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun enableLocationRequest(
        context: Context,
        makeRequest: (intentSenderRequest: IntentSenderRequest) -> Unit//Lambda to call when locations are off.
    ) {
        val locationRequest = LocationRequest.Builder(//Create a location request object
                Priority.PRIORITY_HIGH_ACCURACY,//Self explanatory
                10000//Interval -> shorter the interval more frequent location updates
        ).build()

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())//Checksettings with building a request
        task.addOnSuccessListener { locationSettingsResponse ->

            Timber.i("enableLocationRequest: LocationService Already Enabled")

        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()//Create the request prompt
                    makeRequest(intentSenderRequest)//Make the request from UI
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    fun updateCurrentLocationData(activity: MainActivity) {
        getLastLocation()
    }
}
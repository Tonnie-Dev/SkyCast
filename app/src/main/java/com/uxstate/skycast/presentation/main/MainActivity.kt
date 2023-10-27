package com.uxstate.skycast.presentation.main

import android.content.Context
import android.content.IntentFilter
import android.content.IntentSender
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.ramcosta.composedestinations.DestinationsNavHost
import com.uxstate.skycast.domain.prefs.Theme
import com.uxstate.skycast.presentation.NavGraphs
import com.uxstate.skycast.presentation.home.HomeEvent
import com.uxstate.skycast.presentation.home.HomeViewModel
import com.uxstate.skycast.presentation.settings.SettingsViewModel
//import com.uxstate.skycast.presentation.NavGraphs
import com.uxstate.skycast.ui.theme.SkyCastTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


private var br: LocationProviderChangedReceiver? = null
private var locationRequestLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT
                ), navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        registerLocationRequestLauncher()
        registerBroadcastReceiver()

        setContent {

            val settingsViewModel:SettingsViewModel = hiltViewModel()
            val settingsState by settingsViewModel.state.collectAsStateWithLifecycle()
            val homeState by homeViewModel.state.collectAsStateWithLifecycle()

            val prefs = homeState.appPreferences

            val isDark = when(prefs.theme){

                Theme.SYSTEM -> isSystemInDarkTheme()
                Theme.DARK -> true
                Theme.LIGHT -> false
            }

            val isLocationEnabled = homeState.isLocationEnabled
            if (!isLocationEnabled) {
               enableLocationRequest(this@MainActivity) {//Call this if GPS is OFF.
                    locationRequestLauncher?.launch(it)//Launch it to show the prompt.
                }
            }
            //Show your UI accordingly.
            SkyCastTheme(darkTheme = isDark) {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {

                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }



    //In activity
    private fun registerLocationRequestLauncher() {
        locationRequestLauncher =//We will create a global var
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
                if (activityResult.resultCode == RESULT_OK)
                    homeViewModel.updateCurrentLocationData()//If the user clicks OK to turn on location
                else {
                    if (!homeViewModel.state.value.isLocationEnabled) {//If the user cancels, Still make a check and then exit the activity
                        Toast.makeText(
                                this,
                                "Location access is mandatory to use this feature!!",
                                Toast.LENGTH_SHORT
                        )
                                .show()
                        finish()
                    }
                }
            }
    }

    private fun registerBroadcastReceiver() {
        br = LocationProviderChangedReceiver()
        br!!.init(
                object : LocationProviderChangedReceiver.LocationListener {
                    override fun onEnabled() {
                        homeViewModel.onEvent(HomeEvent.GpsEnabledEvent)
                    }

                    override fun onDisabled() {
                        homeViewModel.onEvent(HomeEvent.GpsDisabledEvent)
                    }
                }
        )
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        registerReceiver(br, filter)
    }




    private fun enableLocationRequest(
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
    override fun onDestroy() {
        super.onDestroy()
        if (br != null) unregisterReceiver(br)
    }
}


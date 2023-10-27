package com.uxstate.skycast.presentation.main

import android.content.IntentFilter
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import com.uxstate.skycast.domain.prefs.Theme
import com.uxstate.skycast.presentation.NavGraphs
import com.uxstate.skycast.presentation.home.HomeViewModel
import com.uxstate.skycast.presentation.settings.SettingsViewModel
//import com.uxstate.skycast.presentation.NavGraphs
import com.uxstate.skycast.ui.theme.SkyCastTheme
import dagger.hilt.android.AndroidEntryPoint



private var br: LocationProviderChangedReceiver? = null
private var locationRequestLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()
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
            val state by settingsViewModel.state.collectAsState()

            val prefs = state.appPreferences

            val isDark = when(prefs.theme){

                Theme.SYSTEM -> isSystemInDarkTheme()
                Theme.DARK -> true
                Theme.LIGHT -> false
            }

            val isLocationEnabled by viewModel.isLocationEnabled.collectAsStateWithLifecycle()
            if (!isLocationEnabled) {
                viewModel.enableLocationRequest(this@MainActivity) {//Call this if GPS is OFF.
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
                    viewModel.updateCurrentLocationData(this)//If the user clicks OK to turn on location
                else {
                    if (!viewModel.isLocationEnabled.value) {//If the user cancels, Still make a check and then exit the activity
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
                        viewModel.isLocationEnabled.value = true//Update our VM
                    }

                    override fun onDisabled() {
                        viewModel.isLocationEnabled.value = false//Update our VM
                    }
                }
        )
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        registerReceiver(br, filter)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (br != null) unregisterReceiver(br)
    }
}


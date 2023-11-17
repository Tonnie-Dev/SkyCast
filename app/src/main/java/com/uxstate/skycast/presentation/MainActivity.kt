package com.uxstate.skycast.presentation


import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.DestinationsNavHost
import com.uxstate.skycast.domain.prefs.Theme
import com.uxstate.skycast.presentation.home.HomeViewModel
import com.uxstate.skycast.presentation.settings.SettingsViewModel
import com.uxstate.skycast.ui.theme.SkyCastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel:HomeViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition{

               !viewModel.state.value.isLoading
            }
        }
        enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT
                ), navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        setContent {

            val viewModel: SettingsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val prefs = state.appPreferences

            val isDark = when (prefs.theme) {

                Theme.SYSTEM -> isSystemInDarkTheme()
                Theme.DARK -> true
                Theme.LIGHT -> false
            }

            SkyCastTheme(darkTheme = isDark) {

                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {

                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }

}


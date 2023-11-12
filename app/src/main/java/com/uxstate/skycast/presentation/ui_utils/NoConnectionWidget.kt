package com.uxstate.skycast.presentation.ui_utils

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.uxstate.skycast.R
import com.uxstate.skycast.domain.connectivity.ConnectivityObserver.Status
import com.uxstate.skycast.presentation.home.HomeState
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.utils.getStringById


@Composable
fun NoConnectionWidget(homeState: HomeState, onRetry: () -> Unit) {

    val networkStatus = homeState.netWorkStatus
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    if (networkStatus == Status.AVAILABLE) {
        LaunchedEffect(key1 = true, block = {
            val result = snackbarHostState.showSnackbar(
                    message = context.getStringById(R.string.internet_restored_text),
                    actionLabel = context.getStringById(R.string.retry_text),
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true

            )

            when (result) {

                SnackbarResult.ActionPerformed -> {
                    onRetry()
                }

                SnackbarResult.Dismissed -> {

                }
            }

        })

    }






    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
    ) {

        val spacing = LocalSpacing.current
        val infiniteTransition = rememberInfiniteTransition(label = "infinite_color_anim")

        val animatedAlpha by infiniteTransition.animateFloat(
                initialValue = .3f,
                targetValue = .8f,
                label = "",
                animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse // Reverse the animation to make it continuous
                )
        )

        val variantColor = MaterialTheme.colorScheme.onSurfaceVariant

        Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(
                Alignment.Center
        )
        ) {
            Icon(
                    painter = painterResource(id = R.drawable.broken_cloud),
                    tint = variantColor.copy(alpha = animatedAlpha),
                    contentDescription = stringResource(
                            id = R.string.no_internet_text
                    ),
                    modifier = Modifier.size(spacing.spaceExtraLarge)

            )

            Text(text = stringResource(id = R.string.no_internet_text))
        }

        SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
        ) {


            Snackbar(
                    snackbarData = it,
                    containerColor = Color(0xFF4CAF50),
                    contentColor = MaterialTheme.colorScheme.onBackground
            )
        }

    }

}


@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun NoInternetPreviewLight() {

    NoConnectionWidget(HomeState(), onRetry = {})
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun NoInternetPreviewDark() {

    NoConnectionWidget(HomeState(), onRetry = {})
}





package com.uxstate.skycast.presentation.forecast

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.uxstate.skycast.presentation.forecast.tabs.PagerItem
import com.uxstate.skycast.presentation.forecast.tabs.TabItem.*
import com.uxstate.skycast.utils.PAGER_SIZE


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun ForecastScreen(
    navigator: DestinationsNavigator,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val pageState = rememberPagerState(initialPage = 0, pageCount = { PAGER_SIZE })


    Scaffold {

        paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {

            PagerItem(pagerState = pageState, state = state, viewModel = viewModel)

        }
    }

}
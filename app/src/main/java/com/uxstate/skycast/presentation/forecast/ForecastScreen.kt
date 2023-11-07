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


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun ForecastScreen(
    navigator: DestinationsNavigator,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val tabs = listOf(
            DayOne(
                    state = state,
                    onRefreshForecast = viewModel::refreshForecastWeather
            ),
            DayTwo(state = state, onRefreshForecast = viewModel::refreshForecastWeather),
            DayThree(state = state, onRefreshForecast = viewModel::refreshForecastWeather),
            DayFour(state = state, onRefreshForecast = viewModel::refreshForecastWeather),
            DayFive(state = state, onRefreshForecast = viewModel::refreshForecastWeather)
    )
val pageState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })

    /*  Column ( modifier = Modifier.statusBarsPadding().navigationBarsPadding()){
          DateRow() {

              viewModel.onEvent(ForecastEvent.OnDateChangeEvent(it))
          }
          ForecastContent(
                  state = state,
                  refreshWeatherForecast = viewModel::refreshForecastWeather
          )
      }*/



    Scaffold {


        paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {

            PagerItem(tabs = tabs, pagerState =pageState, state = state, viewModel = viewModel)
           // TabContent(tabs = tabs, pagerState = pageState)
        }
    }

}
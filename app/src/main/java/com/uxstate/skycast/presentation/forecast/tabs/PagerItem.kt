package com.uxstate.skycast.presentation.forecast.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.uxstate.skycast.presentation.forecast.ForecastState
import com.uxstate.skycast.presentation.forecast.ForecastViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerItem(pagerState: PagerState, state:ForecastState, viewModel:ForecastViewModel) {
    val tabs = listOf(
            TabItem.DayOne(
                    state = state,
                    onRefreshForecast = viewModel::refreshForecastWeather
            ),
            TabItem.DayTwo(state = state, onRefreshForecast = viewModel::refreshForecastWeather),
            TabItem.DayThree(state = state, onRefreshForecast = viewModel::refreshForecastWeather),
            TabItem.DayFour(state = state, onRefreshForecast = viewModel::refreshForecastWeather),
            TabItem.DayFive(state = state, onRefreshForecast = viewModel::refreshForecastWeather)
    )
    val coroutineScope = rememberCoroutineScope()

    TabRow(selectedTabIndex = pagerState.currentPage) {


        tabs.forEachIndexed { index, tabItem ->


            Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    content = {
                        Text(text = tabItem.dayOfWeek)
                        Text(text = tabItem.dayOfTheMonth)
                    }


            )
        }
    }

    HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
    ) { page ->


       Box(
                modifier = Modifier
                        .padding(10.dp)
                        .background(Color.Transparent)
                        .fillMaxWidth()
                       ,
                contentAlignment = Alignment.Center
        ) {
            //Text(text = page.toString(), fontSize = 32.sp)
           tabs[page].content()

        }
    }

}
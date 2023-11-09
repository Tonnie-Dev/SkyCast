package com.uxstate.skycast.presentation.forecast.tabs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uxstate.skycast.presentation.forecast.ForecastState
import com.uxstate.skycast.presentation.forecast.ForecastViewModel
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.utils.PAGER_SIZE
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerItem(pagerState: PagerState, state: ForecastState, viewModel: ForecastViewModel) {

    val spacing = LocalSpacing.current
    val tabs = List(PAGER_SIZE) {

        TabItem.DayOne(
                state = state,
                page = it,
                onRefreshForecast = viewModel::refreshForecastWeather
        )

    }

    val coroutineScope = rememberCoroutineScope()

    TabRow(
            selectedTabIndex = pagerState.currentPage,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer

    ) {

        tabs.forEachIndexed { index, tabItem ->

            Tab(selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    content = {
                        Text(
                                text = tabItem.dayOfWeek,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier

                        )
                        Text(
                                text = tabItem.dayOfTheMonth,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier

                        )

                        Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    }
            )
        }
    }

    HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            beyondBoundsPageCount = 3
    ) { page ->

        Column(
                modifier = Modifier
                        .fillMaxSize()
        ) {
            WeatherForecast(state = state, page = page) {

            }

        }
    }

}
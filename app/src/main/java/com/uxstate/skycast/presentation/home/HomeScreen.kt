package com.uxstate.skycast.presentation.home

import androidx.compose.foundation.ScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.uxstate.skycast.utils.Resource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(modifier: Modifier, uiState: HomeState,  refreshWeather:()-> Unit) {




}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(modifier: Modifier, pullRefreshState: PullRefreshState,scrollState:ScrollState, isLoading: Resource.Loading) {

}
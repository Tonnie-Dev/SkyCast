package com.uxstate.skycast.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun HomeScreen(modifier: Modifier, uiState: HomeState,  refreshWeather:()-> Unit) {

    val isLoading = uiState.isLoading


}
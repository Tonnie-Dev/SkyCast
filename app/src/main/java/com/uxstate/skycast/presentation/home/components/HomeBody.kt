package com.uxstate.skycast.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    location: String,
    lastFetchTime: String,
    temperature: String,
    weatherType: String,
    @DrawableRes icon: Int
) {


    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = location, style = MaterialTheme.typography.headlineLarge)

        Text(text = lastFetchTime, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding())
    }
}
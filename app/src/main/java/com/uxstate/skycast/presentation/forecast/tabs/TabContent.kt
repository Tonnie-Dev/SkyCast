package com.uxstate.skycast.presentation.forecast.tabs


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabContent(tabs: List<TabItem>, pagerState: PagerState) {


    HorizontalPager(
            state = pagerState,
            beyondBoundsPageCount = 5,
            userScrollEnabled = true,
            modifier = Modifier.fillMaxSize()
    ) {

        page ->

        tabs[page].content
    }
}
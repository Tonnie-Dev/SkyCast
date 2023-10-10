package com.uxstate.skycast.presentation.forecast.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.uxstate.skycast.ui.theme.LocalSpacing
import com.uxstate.skycast.utils.displayText
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Day(date: LocalDate, isSelected: Boolean, onClick: (date: LocalDate) -> Unit) {

    val spacing = LocalSpacing.current

    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable { onClick(date) }, contentAlignment = Alignment.Center)
    {


        Column(
                modifier = Modifier.padding(vertical = spacing.spaceSmall),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
        ) {
            Text(
                    text = date.dayOfWeek.displayText(),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Light,
            )
            Text(
                    text = dateFormatter.format(date),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
            )
        }
        if (isSelected) {
            Box(
                    modifier = Modifier
                            .fillMaxWidth()
                            .height(spacing.spaceSmall)
                            .background(color = MaterialTheme.colorScheme.primary)
                            .align(Alignment.BottomCenter),
            )
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
private val dateFormatter = DateTimeFormatter.ofPattern("dd")
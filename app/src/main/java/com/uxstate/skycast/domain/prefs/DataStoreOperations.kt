package com.uxstate.skycast.domain.prefs

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    val appPreferences: Flow<AppPreferences>

    suspend fun updateTheme(theme: Theme)

    suspend fun updateTempUnit(unit: TempUnit)

    suspend fun updateCityId(cityId: Int)
}

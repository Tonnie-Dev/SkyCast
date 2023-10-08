package com.uxstate.skycast.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.uxstate.skycast.domain.prefs.AppPreferences
import com.uxstate.skycast.domain.prefs.DataStoreOperations
import com.uxstate.skycast.domain.prefs.TempUnit
import com.uxstate.skycast.domain.prefs.Theme
import com.uxstate.skycast.utils.PREFS_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreOperationsImpl(@ApplicationContext context: Context) : DataStoreOperations {

    private val Context.dataStore by preferencesDataStore(name = PREFS_NAME)
    override val appPreferences: Flow<AppPreferences> =
        context.dataStore.data

                .catch {

                     e ->

                    if (e is IOException){
                        emit(emptyPreferences())
                    } else throw e
                } .map {

                    prefs ->

                    val theme = Theme.valueOf(prefs[ SELECTED_THEME] ?: Theme.SYSTEM.name)
                    val unit = TempUnit.valueOf(prefs[ SELECTED_TEMP_UNIT] ?: TempUnit.CELSIUS.name)
                    val cityId = prefs[CITY_ID] ?: 0

                    AppPreferences(theme = theme, tempUnit = unit, savedCityId = cityId)
                }

    override suspend fun updateTheme(theme: Theme) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTempUnit(unit: TempUnit) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCityId(cityId: Int) {
        TODO("Not yet implemented")
    }

    //PrefsKeysObject
   private companion object {

        val SELECTED_THEME = stringPreferencesKey("selected_theme")
        val SELECTED_TEMP_UNIT = stringPreferencesKey("selected_unit")
        val CITY_ID = intPreferencesKey("city_id")

    }
}
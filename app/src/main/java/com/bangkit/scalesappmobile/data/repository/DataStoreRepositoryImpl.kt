package com.bangkit.scalesappmobile.data.repository

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.scalesappmobile.util.Constants.SCALES_DATA_STORE
import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = SCALES_DATA_STORE)

class DataStoreRepositoryImpl @Inject constructor(private val application: Application) :
    DataStoreRepository {
    override suspend fun saveOnBoarding() {
        application.datastore.edit { preferences ->
            preferences[ON_BOARDING] = true
        }
    }

    override fun getOnBoarding(): Flow<Boolean> {
        return application.datastore.data.map { preferences ->
            preferences[ON_BOARDING] ?: false
        }
    }


    companion object {
        val ON_BOARDING = booleanPreferencesKey("onBoarding")
    }
}
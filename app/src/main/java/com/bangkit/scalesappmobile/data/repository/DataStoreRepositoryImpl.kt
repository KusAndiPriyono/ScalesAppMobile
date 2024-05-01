package com.bangkit.scalesappmobile.data.repository

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import com.bangkit.scalesappmobile.util.Constants.ACCESS_TOKEN
import com.bangkit.scalesappmobile.util.Constants.SCALES_DATA_STORE
import com.bangkit.scalesappmobile.util.Constants.USER_ID
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

    override fun getAccessToken(): Flow<String?> {
        return application.datastore.data.map { preferences ->
            preferences[ACCESS_TOKEN]
        }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        application.datastore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun deleteAccessToken() {
        application.datastore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
        }
    }

    override fun getUserId(): Flow<String?> {
        return application.datastore.data.map { preferences ->
            preferences[USER_ID]
        }
    }

    override suspend fun saveUserId(userId: String) {
        application.datastore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    override suspend fun clear() {
        application.datastore.edit { preferences ->
            preferences.clear()
        }
    }


    companion object {
        val ON_BOARDING = booleanPreferencesKey("onBoarding")
    }
}
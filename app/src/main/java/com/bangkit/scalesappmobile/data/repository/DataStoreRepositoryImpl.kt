package com.bangkit.scalesappmobile.data.repository

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import com.bangkit.scalesappmobile.util.Constants.SCALES_DATA_STORE
import com.bangkit.scalesappmobile.util.Constants.TOKEN
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
            preferences[TOKEN]
        }
    }

    override suspend fun saveAccessToken(token: String) {
        application.datastore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    override suspend fun deleteAccessToken() {
        application.datastore.edit { preferences ->
            preferences.remove(TOKEN)
        }
    }

    override fun getUserId(): Flow<String?> {
        return application.datastore.data.map { preferences ->
            preferences[USER_ID]
        }
    }

    override suspend fun saveUserId(id: String) {
        application.datastore.edit { preferences ->
            preferences[USER_ID] = id
        }
    }

    override fun getUserRole(): Flow<String?> {
        return application.datastore.data.map { preferences ->
            preferences[ROLE]
        }
    }

    override suspend fun saveUserRole(role: String) {
        application.datastore.edit { preferences ->
            preferences[ROLE] = role
        }
    }

    override suspend fun clear() {
        application.datastore.edit { preferences ->
            preferences.clear()
        }
    }


    companion object {
        val ON_BOARDING = booleanPreferencesKey("onBoarding")
        val EMAIL = stringPreferencesKey("email")
        val NAME = stringPreferencesKey("name")
        val PHOTO = stringPreferencesKey("photo")
        val ROLE = stringPreferencesKey("role")
    }
}
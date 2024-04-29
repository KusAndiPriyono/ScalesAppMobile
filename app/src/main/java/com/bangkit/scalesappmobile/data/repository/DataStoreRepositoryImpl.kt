package com.bangkit.scalesappmobile.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.scalesappmobile.data.util.Constants.SCALES_DATA_STORE
import com.bangkit.scalesappmobile.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = SCALES_DATA_STORE)

class DataStoreRepositoryImpl(private val context: Context) : DataStoreRepository {
    override suspend fun saveOnBoarding() {
        context.datastore.edit { preferences ->
            preferences[ON_BOARDING] = true
        }
    }

    override fun getOnBoarding(): Flow<Boolean?> {
        return context.datastore.data.map { it[ON_BOARDING] }
    }

    override suspend fun saveLogin() {
        context.datastore.edit { preferences ->
            preferences[LOGIN] = true
        }
    }

    override suspend fun logout() {
        context.datastore.edit {
            it.clear()
            it[ON_BOARDING] = true
        }
    }

    override fun getLogin(): Flow<Boolean?> {
        return context.datastore.data.map { it[LOGIN] }
    }

    override fun getToken(): Flow<String> {
        return context.datastore.data.map { it[TOKEN] ?: "" }
    }

    override suspend fun saveToken(token: String) {
        context.datastore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    companion object {
        val ON_BOARDING = booleanPreferencesKey("onBoarding")
        val LOGIN = booleanPreferencesKey("login")
        val TOKEN = stringPreferencesKey("token")
    }
}
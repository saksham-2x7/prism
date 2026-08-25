package com.prism.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prism_prefs")

class PreferencesManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PHOTO_URL = stringPreferencesKey("user_photo_url")
    }

    val isDarkThemeFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_DARK_THEME] ?: true
    }

    val isLoggedInFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    val userNameFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_NAME] ?: ""
    }

    val userEmailFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_EMAIL] ?: ""
    }

    val userPhotoUrlFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_PHOTO_URL] ?: ""
    }

    suspend fun setDarkTheme(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDark
        }
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun setUserDetails(name: String, email: String, photoUrl: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = name
            preferences[USER_EMAIL] = email
            preferences[USER_PHOTO_URL] = photoUrl
        }
    }
}

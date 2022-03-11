package com.pepsidrc.fleet_tracker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pepsidrc.fleet_tracker.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DataStoreManager(val context: Context) : ViewModel() {


    // A getter for the username and password value flow
    val username : Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[NAME] ?: ""
        }

    val password : Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[PASSWORD] ?: ""
        }

    companion object {
        private val CREDENTIALS: String = "credentials"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = CREDENTIALS)
        val NAME = stringPreferencesKey("USERNAME")
        val PASSWORD = stringPreferencesKey("PASSWORD")
    }

    suspend fun saveCredentialstoDataStore(usr: UserModel) {
        viewModelScope.launch {
            context.dataStore.edit { credentials ->
                credentials[NAME] = usr.name
                credentials[PASSWORD] = usr.password
            }
        }// viewModelScope Ending here
    }

//    fun getCredentialsFromDataStore() {
//        viewModelScope.launch {
//            username = context.dataStore.data
//                .map { preferences ->
//                    // No type safety.
//                    preferences[NAME] ?: "testUser"
//                }
//            password = context.dataStore.data
//                .map { preferences ->
//                    // No type safety.
//                    preferences[PASSWORD] ?: "testPassword"
//                }
//        }// viewModelScope Ending here
//    }









}

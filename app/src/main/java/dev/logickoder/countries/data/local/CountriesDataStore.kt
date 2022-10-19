package dev.logickoder.countries.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dev.logickoder.countries.utils.SingletonHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CountriesDataStore(
    private val context: Context
) {
    suspend fun save(key: Preferences.Key<String>, data: String) {
        context.local.edit { preferences ->
            preferences[key] = data
        }
    }

    fun get(key: Preferences.Key<String>): Flow<String?> {
        return context.local.data.map { preferences ->
            preferences[key]
        }
    }

    companion object : SingletonHolder<CountriesDataStore, Context>(::CountriesDataStore) {
        private val Context.local: DataStore<Preferences> by preferencesDataStore(
            name = "countries"
        )
    }
}
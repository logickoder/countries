package dev.logickoder.countries.data.repository

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.logickoder.countries.data.local.CountriesDataStore
import dev.logickoder.countries.domain.AppTheme
import dev.logickoder.countries.utils.SingletonHolder
import kotlinx.coroutines.flow.map

class SettingsRepository private constructor(
    context: Context
) {
    private val local = CountriesDataStore.getInstance(context)

    val theme = local.get(THEME).map { data ->
        if (data != null) {
            AppTheme.valueOf(data)
        } else AppTheme.System
    }

    suspend fun setTheme(theme: AppTheme) {
        local.save(THEME, theme.name)
    }

    companion object : SingletonHolder<SettingsRepository, Context>(::SettingsRepository) {
        private val THEME = stringPreferencesKey("theme")
    }
}
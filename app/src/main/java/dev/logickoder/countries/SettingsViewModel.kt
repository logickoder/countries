package dev.logickoder.countries

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.logickoder.countries.data.local.CountriesDataStore
import dev.logickoder.countries.domain.AppTheme
import dev.logickoder.countries.utils.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SettingsViewModel private constructor(
    context: Context
) {
    private val local = CountriesDataStore.getInstance(context)

    val theme = local.get(THEME).map { data ->
        if (data != null) {
            AppTheme.valueOf(data)
        } else AppTheme.System
    }.flowOn(Dispatchers.Default)

    suspend fun setTheme(theme: AppTheme) {
        local.save(THEME, theme.name)
    }

    companion object : SingletonHolder<SettingsViewModel, Context>(::SettingsViewModel) {
        private val THEME = stringPreferencesKey("theme")
    }
}

@Composable
fun rememberSettingsViewModel(): SettingsViewModel {
    val context = LocalContext.current
    return remember {
        SettingsViewModel.getInstance(context)
    }
}
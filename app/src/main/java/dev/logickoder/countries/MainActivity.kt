package dev.logickoder.countries

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeComponentActivity
import dev.logickoder.countries.data.repository.SettingsRepository
import dev.logickoder.countries.domain.AppTheme
import dev.logickoder.countries.navigation.Navigation
import dev.logickoder.countries.presentation.theme.CountriesTheme

class MainActivity : NodeComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            CountriesTheme(darkTheme = isDarkMode()) {
                // A surface container using the 'background' color from the theme
                NodeHost(
                    integrationPoint = integrationPoint,
                    factory = { context ->
                        Navigation(
                            buildContext = context,
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun isDarkMode(): Boolean {
        val context = LocalContext.current
        val repository = remember {
            SettingsRepository.getInstance(context)
        }
        val theme by repository.theme.collectAsState(initial = AppTheme.System)
        val darkTheme = isSystemInDarkTheme()
        return remember(theme) {
            when (theme) {
                AppTheme.Light -> false
                AppTheme.Dark -> true
                AppTheme.System -> darkTheme
            }
        }
    }
}
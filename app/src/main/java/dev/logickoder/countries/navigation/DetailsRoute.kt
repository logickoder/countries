package dev.logickoder.countries.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import dev.logickoder.countries.domain.AppTheme
import dev.logickoder.countries.domain.CountryDetail
import dev.logickoder.countries.presentation.details.DetailsScreen
import dev.logickoder.countries.rememberCountriesViewModel
import dev.logickoder.countries.rememberSettingsViewModel
import kotlinx.coroutines.launch

class DetailsRoute(
    buildContext: BuildContext,
    private val detail: CountryDetail,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val countriesVm = rememberCountriesViewModel()
        val settingsVm = rememberSettingsViewModel()
        val scope = rememberCoroutineScope()
        val onTheme: (AppTheme) -> Unit = remember {
            { scope.launch { settingsVm.setTheme(it) } }
        }
        val onBorder: (String) -> Unit = remember {
            {
                scope.launch {
                    backStack.push(Navigation.Route.Details(countriesVm.getCountryDetail(it)))
                }
            }
        }
        val onBack = remember {
            {
                backStack.pop()
            }
        }

        val theme by settingsVm.theme.collectAsState(initial = AppTheme.System)
        DetailsScreen(
            detail = detail,
            theme = theme,
            modifier = modifier,
            onBack = onBack,
            onTheme = onTheme,
            onBorder = onBorder,
        )
    }
}
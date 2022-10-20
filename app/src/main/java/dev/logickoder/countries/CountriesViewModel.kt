package dev.logickoder.countries

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.logickoder.countries.data.local.CountriesDataStore
import dev.logickoder.countries.data.model.Country
import dev.logickoder.countries.data.model.CountryNetwork
import dev.logickoder.countries.data.model.toCountry
import dev.logickoder.countries.data.remote.CountriesLoader
import dev.logickoder.countries.domain.Continent
import dev.logickoder.countries.domain.CountryDetail
import dev.logickoder.countries.utils.ResultWrapper
import dev.logickoder.countries.utils.SingletonHolder
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CountriesViewModel private constructor(
    context: Context,
) {
    private val remote = CountriesLoader
    private val local = CountriesDataStore.getInstance(context)

    private val _region = MutableStateFlow(Continent.Africa)
    val region = _region.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    private val localCountries = local.get(COUNTRIES).map {
        _isRefreshing.update { true }
        val result = it.decode()
        _isRefreshing.update { false }
        result
    }.flowOn(Dispatchers.Default)

    val countries = combine(
        flow = _region,
        flow2 = localCountries,
        flow3 = _search,
        transform = { region, countries, search ->
            _isRefreshing.update { true }
            val result = countries.filter(region, search).toImmutableList()
            _isRefreshing.update { false }
            result
        }
    ).flowOn(Dispatchers.Default)

    fun search(search: String) {
        _search.update { search }
    }

    fun updateRegion(continent: Continent) {
        _region.update { continent }
    }

    suspend fun getCountryDetail(
        code: String
    ): CountryDetail = withContext(Dispatchers.Default) {
        val countries = localCountries.first()
        // get the country from the code
        val country = countries.first { it.code == code }
        // then get the border countries
        val borders = countries.filter { it.code in country.borders }.map {
            CountryDetail.Border(it.name, it.code)
        }
        return@withContext CountryDetail(
            country = country,
            borders = borders,
        )
    }

    suspend fun refreshCountries(): ResultWrapper<Unit> = withContext(Dispatchers.IO) {
        _isRefreshing.update { true }
        val result = when (val data = remote.getCountries()) {
            is ResultWrapper.Failure -> {
                ResultWrapper.Failure(data.error)
            }

            is ResultWrapper.Success -> {
                local.save(COUNTRIES, Json.encodeToString(data.data))
                ResultWrapper.Success(Unit)
            }
        }
        _isRefreshing.update { false }
        return@withContext result
    }

    private fun String?.decode() = this?.let { data ->
        Json.decodeFromString<List<CountryNetwork>>(data).map { it.toCountry() }
    } ?: emptyList()

    private fun List<Country>.filter(continent: Continent, search: String) = filter {
        it.continent == continent && it.name.contains(search, true)
    }.sortedBy { it.name }

    companion object : SingletonHolder<CountriesViewModel, Context>(::CountriesViewModel) {
        private val COUNTRIES = stringPreferencesKey("countries")
    }
}

@Composable
fun rememberCountriesViewModel(): CountriesViewModel {
    val context = LocalContext.current
    return remember {
        CountriesViewModel.getInstance(context)
    }
}
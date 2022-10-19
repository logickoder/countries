package dev.logickoder.countries

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.logickoder.countries.data.local.CountriesDataStore
import dev.logickoder.countries.data.model.Country
import dev.logickoder.countries.data.model.CountryNetwork
import dev.logickoder.countries.data.model.toCountry
import dev.logickoder.countries.data.remote.CountriesLoader
import dev.logickoder.countries.domain.Continent
import dev.logickoder.countries.utils.ResultWrapper
import dev.logickoder.countries.utils.SingletonHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
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

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    val countries = combine(
        flow = _region,
        flow2 = local.get(COUNTRIES),
        flow3 = _search,
        transform = { region, countries, search ->
            countries.decode().filter(region, search)
        }
    ).flowOn(Dispatchers.Default)

    fun search(search: String) {
        _search.update { search }
    }

    fun updateRegion(continent: Continent) {
        _region.update { continent }
    }

    suspend fun refreshCountries(): ResultWrapper<Unit> = withContext(Dispatchers.IO) {
        return@withContext when (val data = remote.getCountries()) {
            is ResultWrapper.Failure -> {
                ResultWrapper.Failure(data.error)
            }

            is ResultWrapper.Success -> {
                local.save(COUNTRIES, Json.encodeToString(data.data))
                ResultWrapper.Success(Unit)
            }
        }
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
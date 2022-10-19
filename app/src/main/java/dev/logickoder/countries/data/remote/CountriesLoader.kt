package dev.logickoder.countries.data.remote

import dev.logickoder.countries.data.model.CountryNetwork
import dev.logickoder.countries.utils.ResultWrapper
import io.ktor.client.call.body
import io.ktor.client.request.get

object CountriesLoader {

    private const val ROUTE = "https://restcountries.com/v3.1/all"

    suspend fun getCountries(): ResultWrapper<List<CountryNetwork>> {
        return RemoteClient.call {
            get(ROUTE) {}.body()
        }
    }
}
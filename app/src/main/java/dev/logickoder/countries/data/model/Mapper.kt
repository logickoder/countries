package dev.logickoder.countries.data.model

import dev.logickoder.countries.domain.Continent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive

fun CountryNetwork.toCountry(): Country {
    return Country(
        borders = borders,
        capital = capital.firstOrNull() ?: "",
        code = cca3,
        continent = Continent.values().first { it.readableName.equals(continents.first(), true) },
        currencies = currencies?.let { data ->
            buildList {
                data.entries.forEach { entry ->
                    add(Json.decodeFromJsonElement(entry.value))
                }
            }
        } ?: emptyList(),
        flag = flags?.entries?.firstOrNull()?.value?.jsonPrimitive?.content ?: "",
        languages = languages?.entries?.map {
            it.value.jsonPrimitive.content
        } ?: emptyList(),
        name = name.common,
        nativeName = name.nativeName?.entries?.firstOrNull()?.let {
            Json.decodeFromJsonElement<Name>(it.value).common
        } ?: name.official,
        population = population,
        region = region,
        subregion = subregion,
        domains = tld,
    )
}
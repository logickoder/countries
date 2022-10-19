package dev.logickoder.countries.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class CountryNetwork(
    @SerialName("borders")
    val borders: List<String> = listOf(),
    @SerialName("capital")
    val capital: List<String> = listOf(),
    @SerialName("cca3")
    val cca3: String = "", // BGR
    @SerialName("continents")
    val continents: List<String> = listOf(),
    /**
     * Each object in the currencies field can be converted to a currency object
     */
    @SerialName("currencies")
    val currencies: JsonObject? = null,
    /**
     * Each object in the flags field represents the url to the flag
     *
     * "png":"https://flagcdn.com/w320/bg.png",
     * "svg":"https://flagcdn.com/bg.svg"
     */
    @SerialName("flags")
    val flags: JsonObject? = null,
    /**
     * Each object in the [languages] field can be converted to a string that represents the language
     */
    @SerialName("languages")
    val languages: JsonObject? = null,
    @SerialName("name")
    val name: Name = Name(),
    @SerialName("population")
    val population: Int = 0, // 6927288
    @SerialName("region")
    val region: String = "", // Europe
    @SerialName("subregion")
    val subregion: String = "", // Southeast Europe
    @SerialName("tld")
    val tld: List<String> = listOf()
)
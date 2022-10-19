package dev.logickoder.countries.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    @SerialName("name")
    val name: String = "", // Bulgarian lev
    @SerialName("symbol")
    val symbol: String = "" // лв
)

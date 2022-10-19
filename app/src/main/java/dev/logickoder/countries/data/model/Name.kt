package dev.logickoder.countries.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Name(
    @SerialName("common")
    val common: String = "", // Bulgaria
    /**
     * Each object in the [nativeName] field represents a [Name] object
     *
     * "bul":{
     * "official":"Република България",
     * "common":"България"
     * }
     */
    @SerialName("nativeName")
    val nativeName: JsonObject? = null,
    @SerialName("official")
    val official: String = "" // Republic of Bulgaria
)
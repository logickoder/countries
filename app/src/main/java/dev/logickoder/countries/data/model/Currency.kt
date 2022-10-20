package dev.logickoder.countries.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Currency(
    @SerialName("name")
    val name: String = "", // Bulgarian lev
    @SerialName("symbol")
    val symbol: String = "" // лв
) : Parcelable {
    override fun toString() = name
}

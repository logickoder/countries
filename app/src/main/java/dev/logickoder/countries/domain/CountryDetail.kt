package dev.logickoder.countries.domain

import android.os.Parcelable
import dev.logickoder.countries.data.model.Country
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryDetail(
    val country: Country,
    val borders: List<Border>,
) : Parcelable {

    @Parcelize
    data class Border(
        val name: String,
        val code: String,
    ) : Parcelable
}
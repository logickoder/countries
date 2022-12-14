package dev.logickoder.countries.data.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import dev.logickoder.countries.domain.Continent
import kotlinx.parcelize.Parcelize

@Stable
@Parcelize
data class Country(
    val borders: List<String>,
    val capital: String,
    val code: String,
    val continent: Continent,
    val currencies: List<Currency>,
    val flag: String,
    val languages: List<String>,
    val name: String,
    val nativeName: String,
    val population: Int,
    val region: String,
    val subregion: String,
    val domains: List<String>
) : Parcelable

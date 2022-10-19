package dev.logickoder.countries.domain

enum class Continent(val readableName: String) {
    Africa("Africa"),
    Antarctica("Antarctica"),
    Asia("Asia"),
    Europe("Europe"),
    NorthAmerica("North America"),
    Oceania("Oceania"),
    SouthAmerica("South America");

    override fun toString() = readableName
}
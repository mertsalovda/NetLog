package ru.mertsalovda.netlog.domain.models

data class CountryDto(
    val name: String,
    val flag: String,
    val currencies: List<CurrencyDto>,
)
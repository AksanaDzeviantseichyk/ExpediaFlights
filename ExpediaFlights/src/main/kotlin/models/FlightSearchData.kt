package models

import java.time.LocalDate

data class FlightSearchData(
    val departureCity: String,
    val arrivalCity: String,
    val daysFromTodayDeparture: Int,
    val daysFromDepartureDate: Int,
    val travelers: Travelers
) {

    val departureDate: LocalDate = LocalDate.now().plusDays(daysFromTodayDeparture.toLong())
    val returnDate: LocalDate = departureDate.plusDays(daysFromDepartureDate.toLong())
}
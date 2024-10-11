package models

data class Travelers(
    val adults: Int,
    val children: Int = 0,
    val infants: Int = 0
)
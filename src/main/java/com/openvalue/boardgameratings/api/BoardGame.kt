package com.openvalue.boardgameratings.api

data class BoardGame ( val name: String,
                       val category: Category,
                       val ageRange: AgeRange,
                       val numberOfPlayers: NumberOfPlayers,
                       val rating: Rating
)

enum class Category {
    EDUCATIONAL,
    STRATEGY,
    ADVENTURE
}
data class AgeRange (val minimumAge: Int, val maximumAge: Int = 99)

data class NumberOfPlayers ( val minimalNumberOfPlayers: Int,  val maximumNumberOfPlayers: Int = 4)

data class Rating (val averageRate: Double)
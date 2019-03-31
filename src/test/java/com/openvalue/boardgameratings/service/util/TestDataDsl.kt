package com.openvalue.boardgameratings.service.util

import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity
import com.openvalue.boardgameratings.service.boardgame.Category
import com.openvalue.boardgameratings.service.rating.RateEntity

@DslMarker
annotation class TestDSL

@TestDSL
class BoardGameBuilder(var name: String) {
    private var minimalAge: Int = 10
    private var maximalAge: Int = 99
    private var minNrPlayer: Int = 2
    private var maxNrPlayer: Int = 4

    fun build(): BoardGameEntity {
        return BoardGameEntity(
                id = null,
                name = name,
                category = Category.ADVENTURE,
                minimalAge =  minimalAge,
                maximalAge = maximalAge,
                minimalNumberOfPlayers = minNrPlayer,
                maximalNumberOfPlayers = maxNrPlayer
        )
    }
}

fun dominion (): BoardGameEntity {
    return game { name = "Dominion" }
}

fun monopoly (): BoardGameEntity {
    return game { name = "Monopoly" }
}


fun game(name: String = "", e: BoardGameBuilder.() -> Unit): BoardGameEntity {
    val builder = BoardGameBuilder(name)
    builder.e()
    return builder.build()
}


@TestDSL
class RateEntityBuilder(var assignedTo: String, var rate: Double) {
   fun build() : RateEntity {
       return RateEntity(
               id = null,
               boardGameName = assignedTo,
               rate = rate
       )
   }
}

fun oneOutOfFive(name: String = "", r : RateEntityBuilder.() -> Unit) : RateEntity {
    return rateEntity(1.0, name, r)
}

fun fourOutOfFive(name: String = "", r : RateEntityBuilder.() -> Unit) : RateEntity {
    return rateEntity(4.0, name, r)
}

fun fiveOutOfFive(name: String = "", r : RateEntityBuilder.() -> Unit) : RateEntity {
    return rateEntity(5.0, name, r)
}

private fun rateEntity(d: Double, name: String, r: RateEntityBuilder.() -> Unit): RateEntity {
    val builder = RateEntityBuilder(name, d)
    builder.r()
    return builder.build()
}




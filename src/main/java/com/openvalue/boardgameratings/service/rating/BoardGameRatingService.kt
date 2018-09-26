package com.openvalue.boardgameratings.service.rating

import com.openvalue.boardgameratings.api.*
import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity
import com.openvalue.boardgameratings.service.boardgame.BoardGameNotFound
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class BoardGameRatingService (@Autowired private val boardGameRepository: BoardGameRepository, @Autowired private val rateRepository: RateRepository) {

    @Throws(BoardGameNotFound::class)
    fun ratingBoardGame(rateBoardGame: RateBoardGame): BoardGame {
        val build = RateEntity(null, rateBoardGame.boardGameName, rateBoardGame.rate)
        rateRepository.save(build)

        return boardGameRepository.findByName(rateBoardGame.boardGameName)
                .map { this.ratedBoardGame(it) }
                .orElseThrow { BoardGameNotFound(rateBoardGame.boardGameName) }
    }

    private fun ratedBoardGame(bg: BoardGameEntity): BoardGame {
        val ratesOfTheBoardGame = rateRepository.findByBoardGameName(bg.name)
        val average = ratesOfTheBoardGame.map { it.rate }.average()

        return BoardGame(bg.name, Category.valueOf(bg.category.name),
                AgeRange(bg.minimalAge, bg.maximalAge),
                NumberOfPlayers(bg.minimalNumberOfPlayers, bg.maximalNumberOfPlayers),
                Rating(average, 5)
        )
    }

}

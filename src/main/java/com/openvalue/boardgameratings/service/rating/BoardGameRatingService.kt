package com.openvalue.boardgameratings.service.rating

import com.openvalue.boardgameratings.api.*
import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity
import com.openvalue.boardgameratings.service.boardgame.BoardGameNotFound
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
open class BoardGameRatingService (
        private val boardGameRepository: BoardGameRepository,
        private val rateRepository: RateRepository
) {

    private val logger = KotlinLogging.logger {  }

    @Throws(BoardGameNotFound::class)
    fun ratingBoardGame(rateBoardGame: RateBoardGame): BoardGame {
        val build = RateEntity(null, rateBoardGame.boardGameName, rateBoardGame.rate)

        logger.debug("Saving new rating entity for boardgame ${rateBoardGame.boardGameName}")

        rateRepository.save(build)

        logger.debug("Return updated rate for boardgame ${rateBoardGame.boardGameName}")

        return boardGameRepository.findByName(rateBoardGame.boardGameName)
                .map { this.ratedBoardGame(it) }
                .orElseThrow { BoardGameNotFound(rateBoardGame.boardGameName) }
    }

    fun withHigherRateThan(rate: Double): List<BoardGame> {
        logger.debug("Find all boardgames with rate higher than $rate")

        return boardGameRepository.findAll()
                .map{ this.ratedBoardGame(it) }
                .filter { it.rating.averageRate > rate }
    }
    private fun ratedBoardGame(bg: BoardGameEntity): BoardGame {
        logger.debug("Retrieving all the rates of boardgame ${bg.name}")

        val ratesOfTheBoardGame = rateRepository.findByBoardGameName(bg.name)
        val average = ratesOfTheBoardGame.asSequence().map { it.rate }.average()

        return BoardGame(bg.name, Category.valueOf(bg.category.name),
                AgeRange(bg.minimalAge, bg.maximalAge),
                NumberOfPlayers(bg.minimalNumberOfPlayers, bg.maximalNumberOfPlayers),
                Rating(average)
        )
    }

}

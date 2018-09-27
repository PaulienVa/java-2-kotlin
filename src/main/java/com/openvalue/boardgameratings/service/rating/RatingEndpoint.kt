package com.openvalue.boardgameratings.service.rating

import com.openvalue.boardgameratings.api.BoardGame
import com.openvalue.boardgameratings.api.request.RatingRequest
import com.openvalue.boardgameratings.service.boardgame.BoardGameNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
open class RatingEndpoint( @Autowired private val ratingService: BoardGameRatingService ) {

    @RequestMapping(path = ["/rate"], method = [ RequestMethod.POST ], consumes = [ "application/json" ])
    fun rate(@RequestBody ratingRequest: RatingRequest): ResponseEntity<BoardGame> {
        return try {
            val rateBoardGame = RateBoardGame(ratingRequest.ratedGame, ratingRequest.rate)
            val ratedBoardGame = ratingService.ratingBoardGame(rateBoardGame)
            ResponseEntity.ok().body(ratedBoardGame)
        } catch (boardGameNotFound: BoardGameNotFound) {
            ResponseEntity.notFound().build()
        } catch (re: RuntimeException) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }

    }
}
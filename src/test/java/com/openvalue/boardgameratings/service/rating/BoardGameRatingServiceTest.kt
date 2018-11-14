package com.openvalue.boardgameratings.service.rating

import com.openvalue.boardgameratings.api.Rating
import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository
import com.openvalue.boardgameratings.service.util.TestData.*
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.stubbing.OngoingStubbing
import org.mockito.stubbing.Stubber
import java.util.*
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
internal class BoardGameRatingServiceTest {

    private val boardGameRepository = mock(BoardGameRepository::class.java)

    private val rateRepository = mock(RateRepository::class.java)

    private val boardGameRatingService = BoardGameRatingService(boardGameRepository, rateRepository)

    @Test
    fun `when game is rated then the game with new average rate is returned`() {
        `when`(boardGameRepository.findByName(POPULAR_GAME))
                .returnPopularGame()

        `when`(rateRepository.findByBoardGameName(POPULAR_GAME))
                .returnHighAndLowRate()

        val (_, _, _, _, rating) = boardGameRatingService.ratingBoardGame(RateBoardGame(POPULAR_GAME, lowRate().rate))

        assertEquals(Rating(3.0), rating)
    }

    @Test
    fun `when requesting games with higher rate then 3 only higher games are retrieved`() {
        `when`(boardGameRepository.findAll()).thenReturn(listOf(
                popularBoardGame(),
                notPopularBoardGame()
        ))

        doReturn(listOf(highRate()))
                .whenPopularGameIsRetrieved()
        doReturn(listOf(lowRate()))
                .whenNotPopularGameIsRetrieved()

        val ratedHigherThan3 = boardGameRatingService.withHigherRateThan(3.0)

        assertEquals(1, ratedHigherThan3.size)
        assertNotNull(ratedHigherThan3.find { (name) -> name == POPULAR_GAME })
    }

    fun Stubber.whenPopularGameIsRetrieved() = this.`when`(rateRepository).findByBoardGameName(POPULAR_GAME)
    fun Stubber.whenNotPopularGameIsRetrieved() = this.`when`(rateRepository).findByBoardGameName(NOT_POPULAR_GAME)
    fun OngoingStubbing<Optional<BoardGameEntity>>.returnPopularGame() = this.thenReturn(Optional.of(popularBoardGame()))
    fun OngoingStubbing<List<RateEntity>>.returnHighAndLowRate() = this.thenReturn(listOf(highRate(), lowRate()))

}
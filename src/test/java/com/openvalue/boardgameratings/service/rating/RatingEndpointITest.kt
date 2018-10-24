package com.openvalue.boardgameratings.service.rating

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.openvalue.boardgameratings.api.*
import com.openvalue.boardgameratings.api.request.RatingRequest
import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository
import com.openvalue.boardgameratings.service.util.TestData.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class RatingEndpointITest {

    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var  webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var  boardGameRepository: BoardGameRepository

    @Autowired
    private lateinit var  rateRepository: RateRepository

    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        mvc = webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun `Rating an existing boardGame will update the grade`() {
        boardGameRepository.save(popularBoardGame())
        rateRepository.save(RateEntity(null, POPULAR_GAME, 5.0))

        val dominion = RatingRequest(POPULAR_GAME, 5.0)
        val content = objectMapper.writeValueAsString(dominion)
        mvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
                .andExpect(status().isOk)
    }


    @Test
    fun `Possible to retrieve a game with some higher rate`() {
        boardGameRepository.save(popularBoardGame())
        rateRepository.save(RateEntity(null, POPULAR_GAME, 5.0))
        rateRepository.save(RateEntity(null, POPULAR_GAME, 4.0))

        boardGameRepository.save(notPopularBoardGame())
        rateRepository.save(RateEntity(null, NOT_POPULAR_GAME, 1.0))
        rateRepository.save(RateEntity(null, NOT_POPULAR_GAME, 1.0))

        mvc.perform(
                get("/boardgames?rate=2")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk).andExpect(content().json(expectedGame(popularBoardGame(), 4.5)))
    }


    @AfterEach
    fun cleanUp() {
        rateRepository.deleteAll()
        boardGameRepository.deleteAll()
    }

    @Throws(JsonProcessingException::class)
    private fun expectedGame(en: BoardGameEntity, expectedRate: Double?): String {
        val game = BoardGame(en.name, Category.valueOf(en.category.name),
                AgeRange(en.minimalAge, en.maximalAge),
                NumberOfPlayers(en.minimalNumberOfPlayers, en.maximalNumberOfPlayers),
                Rating(expectedRate!!)
        )

        return objectMapper.writeValueAsString(Arrays.asList(game))

    }
}
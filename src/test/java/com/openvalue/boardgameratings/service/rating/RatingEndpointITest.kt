package com.openvalue.boardgameratings.service.rating

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.openvalue.boardgameratings.api.*
import com.openvalue.boardgameratings.api.request.RatingRequest
import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository
import com.openvalue.boardgameratings.service.util.*
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
    private val popularGame = dominion()
    private val notSoPopularGame = monopoly()

    @BeforeEach
    fun setUp() {
        mvc = webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun `Rating an existing boardGame will update the grade`() {

        // insert a board game
        boardGameRepository.save(popularGame)

        // insert a rate for this game
        rateRepository.save(oneOutOfFive { assignedTo = popularGame.name })

        // rate this game again
        val dominionRateRequest = RatingRequest(popularGame.name, 5.0)
        val content = objectMapper.writeValueAsString(dominionRateRequest)

        // post the rate and verify the response
        mvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        ).andExpect(status().isOk)
    }


    @Test
    fun `Possible to retrieve a game with some higher rate`() {

        // save a popular board game with high rates (on scale of 5)
        boardGameRepository.save(popularGame)
        rateRepository.save(fiveOutOfFive { assignedTo = popularGame.name })
        rateRepository.save(fourOutOfFive { assignedTo = popularGame.name })

        // save a not so popular board game with low rates (on scale of 5)
        boardGameRepository.save(notSoPopularGame)
        rateRepository.save(oneOutOfFive { assignedTo = notSoPopularGame.name })
        rateRepository.save(oneOutOfFive { assignedTo = notSoPopularGame.name })

        // retrieve all the games with a mean rate above 2 and verify the response
        mvc.perform(
                get("/boardgames?rate=2")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk)
         .andExpect(content().json(expectedGame(popularGame, 4.5)))
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
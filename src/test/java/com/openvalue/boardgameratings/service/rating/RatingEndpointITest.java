package com.openvalue.boardgameratings.service.rating;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openvalue.boardgameratings.api.*;
import com.openvalue.boardgameratings.api.request.RatingRequest;
import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity;
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.openvalue.boardgameratings.service.util.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RatingEndpointITest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BoardGameRepository boardGameRepository;

    @Autowired
    private RateRepository rateRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Rating an existing boardGame will update the grade")
    void rate_existing_boardGame_will_update_the_rate() throws Exception {
        // insert a boardgame
        boardGameRepository.save(popularBoardGame());

        // insert a rate for this game
        rateRepository.save(new RateEntity(null, POPULAR_GAME, 5.0d));

        // rate this game again
        final RatingRequest popularGame = new RatingRequest(POPULAR_GAME, 5.0d);

        final String content = objectMapper.writeValueAsString(popularGame);

        // post the rate and verify the response
        mvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
        )
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Possible to retrieve a game with some higher rate")
    void rate_game_with_higher_rate() throws Exception {

        // save a popular board game with high rates (on scale of 5)
        boardGameRepository.save(popularBoardGame());
        rateRepository.save(new RateEntity(null, POPULAR_GAME, 5.0d));
        rateRepository.save(new RateEntity(null, POPULAR_GAME, 4.0d));

        // save a not so popular board game with low rates (on scale of 5)
        boardGameRepository.save(notPopularBoardGame());
        rateRepository.save(new RateEntity(null, NOT_POPULAR_GAME, 1.0d));
        rateRepository.save(new RateEntity(null, NOT_POPULAR_GAME, 1.0d));

        // retrieve all the games with a mean rate above 2 and verify the response
        mvc.perform(
                get("/boardgames?rate=2")
                .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk())
         .andExpect(content().json(
                 expectedGame(popularBoardGame(), 4.5d)
         ));
    }


    @AfterEach
    void cleanUp () {
        rateRepository.deleteAll();
        boardGameRepository.deleteAll();
    }

    private String expectedGame(BoardGameEntity en, Double expectedRate) throws JsonProcessingException {
        BoardGame game = new BoardGame(en.getName(), Category.valueOf(en.getCategory().name()),
                new Rating(expectedRate),
                new AgeRange(en.getMinimalAge(), en.getMaximalAge()),
                new NumberOfPlayers(en.getMinimalNumberOfPlayers(), en.getMaximalNumberOfPlayers())
        );

        return objectMapper.writeValueAsString(Arrays.asList(game));

    }
}
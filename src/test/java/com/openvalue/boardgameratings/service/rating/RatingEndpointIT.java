package com.openvalue.boardgameratings.service.rating;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openvalue.boardgameratings.api.request.RatingRequest;
import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity;
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository;
import com.openvalue.boardgameratings.service.boardgame.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
//@WebMvcTest(RatingEndpoint.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RatingEndpointIT {

//    @Autowired
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
        boardGameRepository.save(boardGame());
        rateRepository.save(new RateEntity(null, NAME, 5.0d, 5));

        final RatingRequest dominion = new RatingRequest(NAME, 5.0);
        final String content = objectMapper.writeValueAsString(dominion);
        mvc.perform(post("/rate")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(content)
        )
        .andExpect(status().isOk());


    }

    private BoardGameEntity boardGame() {
        return BoardGameEntity.builder()
                .category(Category.ADVENTURE)
                .name(NAME)
                .minimalAge(10).maximalAge(99)
                .minimalNumberOfPlayers(2).maximalNumberOfPlayers(4)
                .build();
    }

    private static final String NAME = "Dominion";
}
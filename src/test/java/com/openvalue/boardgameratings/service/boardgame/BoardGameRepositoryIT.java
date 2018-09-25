package com.openvalue.boardgameratings.service.boardgame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.openvalue.boardgameratings.service.util.TestData.boardGame;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoardGameRepositoryIT {

    @Autowired
    private BoardGameRepository repository;

    @Test
    void saving_boardGame_successfully() {
        repository.save(boardGame());

        final List<BoardGameEntity> all = (List<BoardGameEntity>) repository.findAll();
        assertEquals(1, all.size());

    }

}

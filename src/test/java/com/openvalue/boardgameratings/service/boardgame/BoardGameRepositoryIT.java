package com.openvalue.boardgameratings.service.boardgame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoardGameRepositoryIT {

    @Autowired
    private BoardGameRepository repository;

    @Test
    void somethingAwesome() {

        final BoardGameEntity play =  BoardGameEntity.builder()
                .name("Play")
                .category(Category.ADVENTURE)
                .minimalNumberOfPlayers(2).maximalNumberOfPlayers(5)
                .minimalAge(8).maximalAge(12)
                .build();
        repository.save(play);

        final List<BoardGameEntity> all = (List<BoardGameEntity>) repository.findAll();
        assertEquals(1, all.size());

    }

}

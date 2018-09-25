package com.openvalue.boardgameratings.service.util;

import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity;
import com.openvalue.boardgameratings.service.boardgame.Category;

public class TestData {

    public static BoardGameEntity boardGame() {
        return new BoardGameEntity(
                null,
                NAME, Category.ADVENTURE,
                MINIMAL_AGE, MAXIMAL_AGE,
                MIN_NR_PLAYERS, MAX_NR_PLAYERS
        );
    }

    public static final String NAME = "Dominion";
    public static final Integer MINIMAL_AGE = 10;
    public static final Integer MAXIMAL_AGE = 99;
    public static final Integer MIN_NR_PLAYERS = 2;
    public static final Integer MAX_NR_PLAYERS = 4;

}



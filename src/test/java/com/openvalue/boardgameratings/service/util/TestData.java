package com.openvalue.boardgameratings.service.util;

import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity;
import com.openvalue.boardgameratings.service.boardgame.Category;

public class TestData {

    public static BoardGameEntity popularBoardGame() {
        return getBoardGameEntity(POPULAR_GAME);
    }

    public static BoardGameEntity inpopularBoardGame() {
        return getBoardGameEntity(INPOPULAR_GAME);
    }

    private static BoardGameEntity getBoardGameEntity(String inpopularGame) {
        return new BoardGameEntity(
                null,
                inpopularGame, Category.ADVENTURE,
                MINIMAL_AGE, MAXIMAL_AGE,
                MIN_NR_PLAYERS, MAX_NR_PLAYERS
        );
    }


    public static final String POPULAR_GAME = "Dominion";
    public static final String INPOPULAR_GAME = "Monopoly";
    public static final Integer MINIMAL_AGE = 10;
    public static final Integer MAXIMAL_AGE = 99;
    public static final Integer MIN_NR_PLAYERS = 2;
    public static final Integer MAX_NR_PLAYERS = 4;

}



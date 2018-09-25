package com.openvalue.boardgameratings.service.boardgame;

public class BoardGameNotFound extends RuntimeException {
    public BoardGameNotFound(String boardGameIdentifier) {
        super(String.format("Boardgame [%s] not found.", boardGameIdentifier));
    }
}

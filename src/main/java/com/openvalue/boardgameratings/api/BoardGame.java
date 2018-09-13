package com.openvalue.boardgameratings.api;

import lombok.Value;

@Value
public class BoardGame {
    private final String name;
    private final AgeRange ageRange;
    private final NumberOfPlayers numberOfPlayers;
    private final Rating rating;
    private final Category category;

}

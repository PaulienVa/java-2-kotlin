package com.openvalue.boardgameratings.api;

import lombok.Value;

@Value
public class NumberOfPlayers {
    private Integer minimalNumberOfPlayers;
    private Integer maximumNumberOfPlayers; // should have default value
}

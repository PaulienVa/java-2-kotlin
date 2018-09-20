package com.openvalue.boardgameratings.api.request;

import lombok.Value;

@Value
public class RatingRequest {
    private final String ratedGame;
    private final Double rate;

    public boolean isValid() {
        return !ratedGame.isEmpty() && rate != null;
    }
}

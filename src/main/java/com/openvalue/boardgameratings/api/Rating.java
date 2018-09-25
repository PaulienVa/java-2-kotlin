package com.openvalue.boardgameratings.api;

import lombok.Value;

@Value
public class Rating {
    private final Double averageRate;
    private final Integer scale;
    //todo maybe some more

    public static class Rate {
    }
}

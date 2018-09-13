package com.openvalue.boardgameratings.api;

import lombok.Value;

@Value
public class AgeRange {
    private final Integer minimumAge;
    private final Integer maximumAge;
}

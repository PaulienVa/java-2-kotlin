package com.openvalue.boardgameratings.api.request;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class RatingRequest {
    @NotBlank
    private final String ratedGame;

    @NotNull
    private final Double rate;
}

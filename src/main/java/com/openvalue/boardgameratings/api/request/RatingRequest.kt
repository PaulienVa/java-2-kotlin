package com.openvalue.boardgameratings.api.request

import javax.validation.constraints.NotBlank

data class RatingRequest(@NotBlank val ratedGame: String, val rate: Double)
package com.openvalue.boardgameratings.service.events;

import com.openvalue.boardgameratings.api.Rating;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RateBoardGame extends ApplicationEvent {

    private String boardGameId;
    private Rating.Rate rate;

    public RateBoardGame(Object source, String boardGameId, Rating.Rate rate) {
        super(source);
        this.boardGameId = boardGameId;
        this.rate = rate;
    }
}

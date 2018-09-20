package com.openvalue.boardgameratings.service.boardgame;

import com.openvalue.boardgameratings.api.request.RatingRequest;
import com.openvalue.boardgameratings.service.events.RateBoardGame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.openvalue.boardgameratings.api.BoardGame;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@Controller
public class RatingEndpoint {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public RatingEndpoint(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @RequestMapping(
            path = "/rate",
            method = POST
    )
    public ResponseEntity<BoardGame> rate(RatingRequest ratingRequest) {
        log.info("Rating request coming in for game {} with rate {}", ratingRequest.getRatedGame(), ratingRequest.getRate());
        if (ratingRequest.isValid()) {
            return ResponseEntity.ok().build();
//            applicationEventPublisher.publishEvent(new RateBoardGame(this, ratingRequest));
        } else {
            return ResponseEntity.badRequest().build(); // better response, this is not finished yet
        }

    }
}

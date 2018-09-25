package com.openvalue.boardgameratings.service.rating;

import com.openvalue.boardgameratings.api.BoardGame;
import com.openvalue.boardgameratings.api.request.RatingRequest;
import com.openvalue.boardgameratings.service.boardgame.BoardGameNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
public class RatingEndpoint {

    private final BoardGameRatingService ratingService;

    @Autowired
    public RatingEndpoint(BoardGameRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping(
            path = "/rate",
            method = POST,
            consumes = "application/json"
    )
    public ResponseEntity<BoardGame> rate(@RequestBody RatingRequest ratingRequest) {
        log.info("Rating request coming in for game {} with rate {}", ratingRequest.getRatedGame(), ratingRequest.getRate());
        try {
            final RateBoardGame rateBoardGame = new RateBoardGame(ratingRequest.getRatedGame(), ratingRequest.getRate());
            final BoardGame ratedBoardGame = ratingService.ratingBoardGame(rateBoardGame);
            return ResponseEntity.ok().body(ratedBoardGame);
        } catch (BoardGameNotFound boardGameNotFound) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException re) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}

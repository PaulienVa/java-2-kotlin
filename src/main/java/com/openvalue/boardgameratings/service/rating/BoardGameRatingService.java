package com.openvalue.boardgameratings.service.rating;

import com.openvalue.boardgameratings.api.*;
import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity;
import com.openvalue.boardgameratings.service.boardgame.BoardGameNotFound;
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardGameRatingService {
    private final BoardGameRepository boardGameRepository;
    private final RateRepository rateRepository;

    @Autowired
    public BoardGameRatingService(BoardGameRepository boardGameRepository, RateRepository rateRepository) {
        this.boardGameRepository = boardGameRepository;
        this.rateRepository = rateRepository;
    }

    BoardGame ratingBoardGame(RateBoardGame rateBoardGame) throws BoardGameNotFound {
        final RateEntity build = new RateEntity(null, rateBoardGame.getBoardGameName(), rateBoardGame.getRate());

        log.debug("Saving new rating entity for boardgame {}", rateBoardGame.getBoardGameName());

        rateRepository.save(build);

        log.debug("Return updated rate for boardgame {}", rateBoardGame.getBoardGameName());

        return boardGameRepository.findByName(rateBoardGame.getBoardGameName())
                .map(this::ratedBoardGame)
                .orElseThrow(() -> new BoardGameNotFound(rateBoardGame.getBoardGameName()));
    }

    List<BoardGame> withHigherRateThan(Double rate) {
        log.debug("Find all boardgames with rate higher than {}", rate);

        return boardGameRepository.findAll()
                .stream()
                .map(this::ratedBoardGame)
                .filter(game -> game.getRating().getAverageRate() > rate)
                .collect(Collectors.toList());
    }

    private BoardGame ratedBoardGame(BoardGameEntity bg) {
        log.debug("Retrieving all the rates of boardgame {}", bg.getName());

        final List<RateEntity> ratesOfTheBoardGame = rateRepository.findByBoardGameName(bg.getName());
        final Double average = ratesOfTheBoardGame.stream()
                .mapToDouble(RateEntity::getRate)
                .average()
                .getAsDouble();

        return new BoardGame(bg.getName(), Category.valueOf(bg.getCategory().name()),
                new Rating(average),
                new AgeRange(bg.getMinimalAge(), bg.getMaximalAge()),
                new NumberOfPlayers(bg.getMinimalNumberOfPlayers(), bg.getMaximalNumberOfPlayers())
        );
    }
}



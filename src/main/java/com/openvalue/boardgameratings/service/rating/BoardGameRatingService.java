package com.openvalue.boardgameratings.service.rating;

import com.openvalue.boardgameratings.api.*;
import com.openvalue.boardgameratings.service.boardgame.BoardGameEntity;
import com.openvalue.boardgameratings.service.boardgame.BoardGameNotFound;
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        final RateEntity build = RateEntity.builder().rate(rateBoardGame.getRate()).boardGameName(rateBoardGame.getBoardGameName()).scale(5).build();
        rateRepository.save(build);

        return boardGameRepository.findByName(rateBoardGame.getBoardGameName())
                .map(this::ratedBoardGame)
                .orElseThrow(() -> new BoardGameNotFound(rateBoardGame.getBoardGameName()));
    }

    private BoardGame ratedBoardGame(BoardGameEntity bg) {
        final List<RateEntity> ratesOfTheBoardGame = rateRepository.findByBoardGameName(bg.getName());
        final Double average = ratesOfTheBoardGame.stream()
                .mapToDouble(RateEntity::getRate)
                .average()
                .getAsDouble();

        return BoardGame.builder()
                .name(bg.getName())
                .category(Category.valueOf(bg.getCategory().name()))
                .rating(new Rating(average, 5))
                .ageRange(new AgeRange(bg.getMinimalAge(), bg.getMaximalAge()))
                .numberOfPlayers(new NumberOfPlayers(bg.getMinimalNumberOfPlayers(), bg.getMaximalNumberOfPlayers()))
                .build();
    }

}

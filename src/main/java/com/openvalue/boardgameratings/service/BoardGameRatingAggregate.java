package com.openvalue.boardgameratings.service;

import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardGameRatingAggregate {
     private final BoardGameRepository boardGameRepository;

    @Autowired
    public BoardGameRatingAggregate(BoardGameRepository boardGameRepository) {
        this.boardGameRepository = boardGameRepository;
    }

//    public BoardGame ratingBoardGame(RateBoardGame rateBoardGame) {
//        rateBoardGame.getBoardGameId();
//    }

    //todo add plus and minus to show operator overloading
}

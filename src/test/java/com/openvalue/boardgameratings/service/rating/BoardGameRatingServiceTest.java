package com.openvalue.boardgameratings.service.rating;

import com.openvalue.boardgameratings.api.BoardGame;
import com.openvalue.boardgameratings.api.Rating;
import com.openvalue.boardgameratings.api.request.RatingRequest;
import com.openvalue.boardgameratings.service.boardgame.BoardGameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.openvalue.boardgameratings.service.util.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardGameRatingServiceTest {

    @Mock
    private BoardGameRepository boardGameRepository;

    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    private BoardGameRatingService boardGameRatingService;

    @Test
    void when_game_is_rated_then_the_game_with_new_average_rate_is_returned() {
        when(boardGameRepository.findByName(POPULAR_GAME)).thenReturn(Optional.of(
                popularBoardGame()
        ));

        when(rateRepository.findByBoardGameName(POPULAR_GAME)).thenReturn(Arrays.asList(highRate(), lowRate()));

        final BoardGame newlyRatedGame = boardGameRatingService.ratingBoardGame(new RateBoardGame(POPULAR_GAME, lowRate().getRate()));

        assertEquals(new Rating(3.0d), newlyRatedGame.getRating());
    }


    @Test
    void when_requesting_games_with_higher_rate_then_3_only_higher_games_are_retrieved() {
        when(boardGameRepository.findAll()).thenReturn(Arrays.asList(
                popularBoardGame(),
                notPopularBoardGame()
        ));

        doReturn(Arrays.asList(highRate())).when(rateRepository).findByBoardGameName(POPULAR_GAME);
        doReturn(Arrays.asList(lowRate())).when(rateRepository).findByBoardGameName(NOT_POPULAR_GAME);

        final List<BoardGame> ratedHigherThan3 = boardGameRatingService.withHigherRateThan(3.0d);

        assertEquals(1, ratedHigherThan3.size());
        assertEquals(1, ratedHigherThan3.stream().filter(game -> game.getName().equals(POPULAR_GAME)).count());
    }

}
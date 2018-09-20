package com.openvalue.boardgameratings.service.boardgame;

import com.openvalue.boardgameratings.api.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardGameRepository extends CrudRepository<BoardGameEntity, Long> {
}

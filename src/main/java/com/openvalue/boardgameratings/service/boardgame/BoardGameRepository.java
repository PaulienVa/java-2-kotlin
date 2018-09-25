package com.openvalue.boardgameratings.service.boardgame;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardGameRepository extends CrudRepository<BoardGameEntity, Long> {

    Optional<BoardGameEntity> findByName(String name);
}

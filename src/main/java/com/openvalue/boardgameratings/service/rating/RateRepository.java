package com.openvalue.boardgameratings.service.rating;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends CrudRepository<RateEntity, Long> {

    List<RateEntity> findByBoardGameName(String boardGameName);
}

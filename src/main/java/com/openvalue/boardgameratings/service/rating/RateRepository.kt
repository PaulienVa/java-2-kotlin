package com.openvalue.boardgameratings.service.rating

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RateRepository : CrudRepository<RateEntity, Long> {
    fun findByBoardGameName(boardGameName: String): List<RateEntity>

}
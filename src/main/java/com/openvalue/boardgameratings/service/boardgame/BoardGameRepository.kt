package com.openvalue.boardgameratings.service.boardgame

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BoardGameRepository : CrudRepository<BoardGameEntity, Long> {
    fun findByName(name: String): Optional<BoardGameEntity>

    override fun findAll(): List<BoardGameEntity>
}
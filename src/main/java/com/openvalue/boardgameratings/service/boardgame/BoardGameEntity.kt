package com.openvalue.boardgameratings.service.boardgame

import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*


@Entity
data class BoardGameEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "name", nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    val category: Category,

    @Column(name = "minNrOfPlayers", nullable = false)
    val minimalNumberOfPlayers: Int,

    @Column(name = "maxNrOfPlayers", nullable = false)
    val maximalNumberOfPlayers: Int,

    @Column(name = "minimalAge", nullable = false)
    val minimalAge: Int,

    @Column(name = "maximalAge", nullable = false)
    val maximalAge: Int

)
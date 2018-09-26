package com.openvalue.boardgameratings.service.rating

import javax.persistence.*


@Entity
data class RateEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "boardGameName", nullable = false)
    val boardGameName: String,


    @Column(name = "rate", nullable = false)
    val rate: Double

)
package com.openvalue.boardgameratings.service.rating;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Getter
class RateEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "boardGameName", nullable = false)
    private String boardGameName;


    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "scale", nullable = false)
    private Integer scale;
}

package com.openvalue.boardgameratings.service.boardgame;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class BoardGameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "minNrOfPlayers", nullable = false)
    private Integer minimalNumberOfPlayers;
    
    @Column(name = "maxNrOfPlayers", nullable = false)
    private Integer maximalNumberOfPlayers;
    
    @Column(name = "minimalAge", nullable = false)
    private Integer minimalAge;
    
    @Column(name = "maximalAge", nullable = false)
    private Integer maximalAge;

}

package com.pedia.laho.domain.genre;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GENRE_ID")
    Long id;

    String name;

    @Builder
    public Genre(String name) {
        this.name = name;
    }
}

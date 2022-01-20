package com.pedia.laho.domain.junctionTable;

import com.pedia.laho.domain.actor.Actor;
import com.pedia.laho.domain.movie.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MovieActor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID")
    private Movie move;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTOR_ID")
    private Actor actor;

    @Builder
    public MovieActor(Movie movie, Actor actor) {
        this.move = movie;
        this.actor = actor;
    }
}

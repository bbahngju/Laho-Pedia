package com.pedia.laho.domain.junctionTable;

import com.pedia.laho.domain.director.Director;
import com.pedia.laho.domain.movie.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MovieDirector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECT_ID")
    private Director director;

    @Builder
    public MovieDirector(Movie movie, Director director) {
        this.movie = movie;
        this.director = director;
    }
}

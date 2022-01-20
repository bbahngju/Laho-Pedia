package com.pedia.laho.domain.movie;

import com.pedia.laho.domain.junctionTable.MovieDirector;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MOVIE_ID")
    private Long id;

    private Long number;

    private String title;

    private String title_en;

    private Long runtime;

    private String open_date;

    private String grade;

    private String image;

    private String key_word;

    private String plot;

    @OneToMany(mappedBy = "movie")
    private List<MovieDirector> MovieDirector = new ArrayList<>();

    @Builder
    public Movie(Long number, String title, String title_en, Long runtime, String open_date,
                 String grade, String image, String key_word, String plot) {
        this.number = number;
        this.title = title;
        this.title_en = title_en;

        this.runtime = runtime;
        this.open_date = open_date;
        this.grade = grade;

        this.image = image;
        this.key_word = key_word;
        this.plot = plot;
    }
}

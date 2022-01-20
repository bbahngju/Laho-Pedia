package com.pedia.laho.domain;


import com.pedia.laho.domain.director.Director;
import com.pedia.laho.domain.director.DirectorRepository;
import com.pedia.laho.domain.junctionTable.MovieDirector;
import com.pedia.laho.domain.junctionTable.MovieDirectorRepository;
import com.pedia.laho.domain.movie.Movie;
import com.pedia.laho.domain.movie.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MovieDirectorTest {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieDirectorRepository movieDirectorRepository;

    @Test
    public void 영화_감독_연관테이블_테스트() {
        //given
        Long movie_number = 1234L;
        String movie_title = "영화";
        String movie_title_en = "movie";
        Long movie_runtime = 123L;
        String movie_open_date = "2021-01-20";
        String movie_grade = "15세 관람가";
        String movie_image = "http://asdf.jpg";
        String movie_key_word = "추리, 심리";
        String movie_plot = "죽었다 누군가";

        Long director_number = 4321L;
        String director_name = "bbahng";

        Movie movie = Movie.builder().number(movie_number).title(movie_title).title_en(movie_title_en)
                .runtime(movie_runtime).open_date(movie_open_date).grade(movie_grade).image(movie_image)
                .key_word(movie_key_word).plot(movie_plot).build();

        Director director = Director.builder().number(director_number).name(director_name).build();

        movieRepository.save(movie);
        directorRepository.save(director);
        movieDirectorRepository.save(MovieDirector.builder().movie(movie)
                .director(director).build());

        //when
        List<MovieDirector> movieDirectorList = movieDirectorRepository.findAll();

        //then
        MovieDirector movieDirector = movieDirectorList.get(0);
        assertThat(movieDirector.getMovie()).isEqualTo(movie);
        assertThat(movieDirector.getDirector()).isEqualTo(director);

    }
}

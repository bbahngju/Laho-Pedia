package com.pedia.laho.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MovieResponseDto {

    private String movieId;
    private String rank;
    private String title;
    private String openDt;
    private String nation;
    private String posters;
    private String plot;

    @Builder
    public MovieResponseDto(String movieId, String rank, String title, String openDt,
                            String nation, String posters, String plot) {
        this.movieId = movieId;
        this.rank = rank;
        this.title = title;
        this.openDt = openDt;
        this.nation = nation;
        this.posters = posters;
        this.plot = plot;
    }
}

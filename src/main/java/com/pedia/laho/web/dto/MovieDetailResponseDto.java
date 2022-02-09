package com.pedia.laho.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MovieDetailResponseDto {
    private String title;
    private String showTm;
    private String openDt;
    private List<NationInfo> nations;
    private List<GenreInfo> genres;
    private List<DirectorInfo> directors;
    private List<ActorInfo> actors;
    private String watchGrade;

    @Builder
    public MovieDetailResponseDto(String title, String showTm,
                                  String openDt, List<NationInfo> nations, List<GenreInfo> genres,
                                  List<DirectorInfo> directors, List<ActorInfo> actors, String watchGrade) {
        this.title = title;
        this.showTm = showTm;
        this.openDt = openDt;
        this.nations = nations;
        this.genres = genres;
        this.directors = directors;
        this.actors = actors;
        this.watchGrade = watchGrade;
    }
}

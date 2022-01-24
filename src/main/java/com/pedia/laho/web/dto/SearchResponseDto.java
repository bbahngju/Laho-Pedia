package com.pedia.laho.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchResponseDto {

    private String title;
    private String prodYear;
    private String nation;
    private String posters;

    public SearchResponseDto(String title, String prodYear, String nation, String posters) {
        this.title = title;
        this.prodYear = prodYear;
        this.nation = nation;
        this.posters = posters;
    }
}

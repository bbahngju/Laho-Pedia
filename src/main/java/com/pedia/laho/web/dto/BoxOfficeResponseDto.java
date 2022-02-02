package com.pedia.laho.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoxOfficeResponseDto {

    private String rank;
    private String title;
    private String prodYear;

    public BoxOfficeResponseDto(String rank, String title, String prodYear) {
        this.rank = rank;
        this.title = title;
        this.prodYear = prodYear;
    }
}

package com.pedia.laho.domain.director;

import com.pedia.laho.domain.junctionTable.MovieDirector;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "DIRECTORS")
@Getter
@NoArgsConstructor
@Entity
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIRECTOR_ID")
    private Long id;

    private Long number;

    private String name;

    @Builder
    public Director(Long number, String name) {
        this.number = number;
        this.name = name;
    }
}

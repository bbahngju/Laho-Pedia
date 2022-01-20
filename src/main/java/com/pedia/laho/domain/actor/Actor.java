package com.pedia.laho.domain.actor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTOR_ID")
    Long id;

    Long number;

    String name;

    @Builder
    public Actor(Long number, String name) {
        this.number = number;
        this.name = name;
    }
}

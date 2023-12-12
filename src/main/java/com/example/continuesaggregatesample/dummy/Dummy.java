package com.example.continuesaggregatesample.dummy;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;


@Entity
@Table(name = "dummy")
@Data
@IdClass(GeneralId.class)
public class Dummy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Id
    @Column(nullable = false, updatable = false)
    private Instant time;

    @Column(columnDefinition = "text")
    private String team;

    private Integer testone;

    private Long testtwo;

    private Double testthree;

}
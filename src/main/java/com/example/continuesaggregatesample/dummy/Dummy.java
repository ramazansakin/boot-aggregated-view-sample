package com.example.continuesaggregatesample.dummy;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Instant;


@Entity
@Table(name = "dummy")
@Data
@NoArgsConstructor
public class Dummy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Instant time;

    @Column(columnDefinition = "text")
    private String team;

    private Integer testone;

    private Long testtwo;

    private Double testthree;

}
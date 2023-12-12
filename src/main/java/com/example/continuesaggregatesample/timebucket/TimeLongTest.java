package com.example.continuesaggregatesample.timebucket;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "time_long_test")
@Data
@IdClass(CompositePK.class)
public class TimeLongTest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Id
    @Column(nullable = false, updatable = false)
    private Long time;

    private String groupName;

    private Integer test;

}
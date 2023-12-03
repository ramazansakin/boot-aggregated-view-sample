package com.example.continuesaggregatesample.condition;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AggregatedConditions {

    private Long id;
    private Timestamp bucket;
    private String device;
    private String location;
    private Double maxTemperature;
    private Double avgHumidity;
    private Integer sumTestOne;
    private Integer minTestTwo;

}
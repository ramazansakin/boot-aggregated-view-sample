package com.example.continuesaggregatesample.condition;

import lombok.Value;

import java.sql.Timestamp;
import java.util.List;

@Value
public class AggregatedConditions {

    private Timestamp bucket;
    private String device;
    private List<String> relatedLocations;
    private Double averageTemperature;
    private Double averageHumidity;
    private Integer sumTestOne;
    private Integer sumTestTwo;

}
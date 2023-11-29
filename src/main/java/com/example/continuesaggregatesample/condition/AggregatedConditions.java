package com.example.continuesaggregatesample.condition;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedConditions {

    @Id
    private Long id;

    private Long bucket;

    private String device;
    @ElementCollection
    private List<String> relatedLocations;
    private Double averageTemperature;
    private Double averageHumidity;
    private Integer sumTestOne;
    private Integer sumTestTwo;

}
package com.example.continuesaggregatesample.condition;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;


@Entity
@Table(name = "conditions")
@Data
public class Conditions {

    @Id
    private Timestamp time;
    private String location;
    private String device;
    private Double temperature;
    private Double humidity;
    private Integer testone;
    private Integer testtwo;

}

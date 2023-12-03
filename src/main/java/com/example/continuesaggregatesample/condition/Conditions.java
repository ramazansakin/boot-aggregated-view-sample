package com.example.continuesaggregatesample.condition;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity
@Table(name = "conditions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CompositeId.class)
public class Conditions {

    @Id
    @Column(name = "serialid", columnDefinition = "bigserial")
    private Long serialid;

    @Id
    private Timestamp time;

    @Column(columnDefinition = "text")
    private String location;

    @Column(columnDefinition = "text")
    private String device;

    private Double temperature;
    private Double humidity;
    private Integer testone;
    private Integer testtwo;

}
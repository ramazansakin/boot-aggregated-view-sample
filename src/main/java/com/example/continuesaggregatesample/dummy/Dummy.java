package com.example.continuesaggregatesample.dummy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "dummy")
@Data
@NoArgsConstructor
public class Dummy {

    @Id
    private Long timestamp;

    @Column(columnDefinition = "text")
    private String cid;

    private Integer testone;

    private Long testtwo;

    private Double testthree;

}

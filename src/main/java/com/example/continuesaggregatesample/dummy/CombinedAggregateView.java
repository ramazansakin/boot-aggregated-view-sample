package com.example.continuesaggregatesample.dummy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombinedAggregateView implements Serializable {

    private Instant bucket;
    private String team;
    private Integer totalTestOne;
    private Long minTestTwo;
    private Long totalOtherTestOne;
    private BigDecimal avgOtherTestTwo;

}
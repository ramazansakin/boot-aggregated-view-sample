package com.example.continuesaggregatesample.dummy;

import java.time.Instant;

public interface HourlyDummyAggregationProjection {
    Instant getBucket();

    String getTeam();

    Long getMaxTestOne();

    Double getMinTestTwo();

    Double getAvgTestThree();
}
package com.example.continuesaggregatesample.dummy;

import java.time.Instant;

public interface AggregatedCombineDummiesProjection {
    Instant getBucket();

    String getTeam();

    Long getTotalTestOne();

    Double getMinTestTwo();

    Long getTotalOtherTestOne();

    Double getAvgOtherTestTwo();
}
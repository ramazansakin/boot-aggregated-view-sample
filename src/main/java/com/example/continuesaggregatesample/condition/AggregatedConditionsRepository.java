package com.example.continuesaggregatesample.condition;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AggregatedConditionsRepository {

    @Query("SELECT c.bucket AS bucket, c.device AS device, c.related_locations AS relatedLocations, " +
            " c.average_temperature AS averageTemperature, c.average_humidity AS averageHumidity, " +
            " c.sum_testone AS sumTestOne, c.sum_testtwo AS sumTestTwo " +
            " FROM conditions_5min_agg c " +
            " WHERE c.bucket = :bucket AND c.device = :device")
    List<AggregatedConditions> findByBucketAndDevice(@Param("bucket") Timestamp bucket, @Param("device") String device);

}
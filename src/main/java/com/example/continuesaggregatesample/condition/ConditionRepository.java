package com.example.continuesaggregatesample.condition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface ConditionRepository extends JpaRepository<Conditions, Timestamp> {

    @Query(value = """
            SELECT * FROM conditions_5min_agg c
            WHERE c.device = :device AND e.timestamp >= :startDate AND e.timestamp <= :endDate
            """, nativeQuery = true)
    Page<AggregatedConditions> findByBucketAndDevice(
            @Param("device") String device,
            @Param("startDate") Timestamp startDate,
            @Param("endDate") Timestamp endDate,
            Pageable pageable
    );

}
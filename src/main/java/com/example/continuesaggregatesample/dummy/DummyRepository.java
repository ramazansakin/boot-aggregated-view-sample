package com.example.continuesaggregatesample.dummy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface DummyRepository extends JpaRepository<Dummy, Integer> {

    @Query(value = "SELECT * FROM Dummy d ORDER BY d.time DESC LIMIT 3", nativeQuery = true)
    List<Dummy> findLastThreeRows();

    @Query(value = """
            SELECT
                a.bucket,
                a.team,
                a.sum_testone AS totalTestOne,
                a.min_testtwo AS minTestTwo,
                r.total_othertestone AS totalOtherTestOne,
                r.avg_othertesttwo AS avgOtherTestTwo
            FROM aggregated_dummies_5mins AS a
                INNER JOIN related_dummy_aggregate_5min AS r
                    ON (a.bucket = r.bucket AND a.team = r.team)
            WHERE a.team = :team AND a.bucket BETWEEN :start AND :end
            ORDER BY bucket DESC
            """, nativeQuery = true)
    List<AggregatedCombineDummiesProjection> findCombinedAggregateView(
            @Param("team") String team,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    @Query(value = """
            SELECT
                h_bucket AS bucket,
                team,
                h_sum_testone AS maxTestOne,
                h_min_testtwo AS minTestTwo,
                h_avg_testthree AS avgTestThree
            FROM aggregated_dummies_hourly
            WHERE team = :team AND h_bucket BETWEEN :start AND :end
            ORDER BY bucket DESC
            """, nativeQuery = true)
    List<HourlyDummyAggregationProjection> findCombinedAggregateViewHourly(
            @Param("team") String team,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    @Query(value = """
            SELECT
                a.bucket,
                a.team,
                a.sum_testone AS totalTestOne,
                a.min_testtwo AS minTestTwo,
                r.total_othertestone AS totalOtherTestOne,
                r.avg_othertesttwo AS avgOtherTestTwo
            FROM aggregated_dummies_5mins AS a
                INNER JOIN related_dummy_aggregate_5min AS r
                    ON (a.bucket = r.bucket AND a.team = r.team)
            WHERE a.team = :team AND a.bucket BETWEEN :start AND :end
            ORDER BY bucket DESC
            """, nativeQuery = true)
    Page<AggregatedCombineDummiesProjection> findCombinedAggregateViewPage(
            @Param("team") String team,
            @Param("start") Instant start,
            @Param("end") Instant end,
            Pageable paging
    );

}
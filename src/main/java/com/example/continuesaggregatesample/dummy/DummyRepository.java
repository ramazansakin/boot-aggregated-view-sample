package com.example.continuesaggregatesample.dummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
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
            """, nativeQuery = true)
    List<Object[]> findCombinedAggregateView(
            @Param("team") String team,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    default List<CombinedAggregateView> getCombinedAggregateView(String team, String start, String end) {
        Instant startTime = Instant.parse(start);
        Instant endTime = Instant.parse(end);
        List<Object[]> resultList = findCombinedAggregateView(team, startTime, endTime);
        List<CombinedAggregateView> combinedAggregateViews = new ArrayList<>();

        for (Object[] result : resultList) {
            Instant bucket = (Instant) result[0];
            String teamName = (String) result[1];
            Integer totalTestOne = (Integer) result[2];
            Long minTestTwo = (Long) result[3];
            Long totalOtherTestOne = (Long) result[4];
            BigDecimal avgOtherTestTwo = (BigDecimal) result[5];

            CombinedAggregateView view = new CombinedAggregateView(
                    bucket, teamName, totalTestOne, minTestTwo, totalOtherTestOne, avgOtherTestTwo
            );

            combinedAggregateViews.add(view);
        }

        return combinedAggregateViews;
    }

}
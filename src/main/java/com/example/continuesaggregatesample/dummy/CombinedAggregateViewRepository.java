package com.example.continuesaggregatesample.dummy;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CombinedAggregateViewRepository {

    @Query(value = """
            SELECT  da.timestamp_bucket AS timestamp,
                    da.team, da.total_testone, da.avg_testtwo,
                    rda.total_othertestone, rda.avg_othertesttwo
            FROM dummy_aggregate_5min AS da
            JOIN related_dummy_aggregate_5min rda AS da ON  
            """, nativeQuery = true)
    List<CombinedAggregateView> findCombinedAggregateView();

}
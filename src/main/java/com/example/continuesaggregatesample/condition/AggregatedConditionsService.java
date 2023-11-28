package com.example.continuesaggregatesample.condition;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AggregatedConditionsService {

    private final AggregatedConditionsRepository aggregatedConditionsRepository;

    public List<AggregatedConditions> getAggregatedConditions(Timestamp bucket, String device) {
        return aggregatedConditionsRepository.findByBucketAndDevice(bucket, device);
    }

}
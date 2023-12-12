package com.example.continuesaggregatesample.condition;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AggregatedConditionsService {

    private final ConditionRepository conditionRepository;

    public Page<AggregatedConditions> getAggregatedConditions(String device, Timestamp startDate, Timestamp endDate) {
        return conditionRepository.findByBucketAndDevice(device, startDate, endDate, null);
    }

}
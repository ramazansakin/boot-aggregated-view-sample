package com.example.continuesaggregatesample.resource;

import com.example.continuesaggregatesample.condition.AggregatedConditions;
import com.example.continuesaggregatesample.condition.AggregatedConditionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aggregated-conditions")
public class AggregatedConditionsController {

    private final AggregatedConditionsService aggregatedConditionsService;

    @GetMapping
    public ResponseEntity<Page<AggregatedConditions>> getAggregatedConditions(
            @RequestParam String device,
            @RequestParam String start,
            @RequestParam String end) {

        Timestamp startTime = Timestamp.valueOf(start);
        Timestamp endTime = Timestamp.valueOf(end);
        return ResponseEntity.ok(aggregatedConditionsService
                .getAggregatedConditions(device, startTime, endTime));
    }

}
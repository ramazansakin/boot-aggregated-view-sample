package com.example.continuesaggregatesample.resource;

import com.example.continuesaggregatesample.condition.AggregatedConditions;
import com.example.continuesaggregatesample.condition.AggregatedConditionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aggregated-conditions")
public class AggregatedConditionsController {

    private final AggregatedConditionsService aggregatedConditionsService;

    @GetMapping
    public ResponseEntity<List<AggregatedConditions>> getAggregatedConditions(
            @RequestParam String timestamp,
            @RequestParam String device) {

        Timestamp time = Timestamp.valueOf(timestamp);
        return ResponseEntity.ok(aggregatedConditionsService.getAggregatedConditions(time, device));
    }

}
package com.example.continuesaggregatesample.resource;

import com.example.continuesaggregatesample.dummy.AggregatedCombineDummiesProjection;
import com.example.continuesaggregatesample.dummy.DummyRepository;
import com.example.continuesaggregatesample.dummy.HourlyDummyAggregationProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aggregated-dummies")
public class DummyController {

    private final DummyRepository dummyRepository;

    @GetMapping
    public ResponseEntity<List<AggregatedCombineDummiesProjection>> getAggregatedDummies(
            @RequestParam String team,
            @RequestParam String start,
            @RequestParam String end) {

        Instant startTime = Instant.parse(start);
        Instant endTime = Instant.parse(end);
        return ResponseEntity.ok(dummyRepository.findCombinedAggregateView(team, startTime, endTime));
    }

    @GetMapping("paging")
    public ResponseEntity<Page<AggregatedCombineDummiesProjection>> getAggregatedDummiesPage(
            @RequestParam String team,
            @RequestParam String start,
            @RequestParam String end) {

        Instant startTime = Instant.parse(start);
        Instant endTime = Instant.parse(end);
        Pageable page = PageRequest.of(0, 5 , Sort.by("team").descending());
        return ResponseEntity.ok(dummyRepository.findCombinedAggregateViewPage(team, startTime, endTime, page));
    }

    @GetMapping("hourly")
    public ResponseEntity<List<HourlyDummyAggregationProjection>> getAggregatedDummiesHourly(
            @RequestParam String team,
            @RequestParam String start,
            @RequestParam String end) {

        Instant startTime = Instant.parse(start);
        Instant endTime = Instant.parse(end);
        return ResponseEntity.ok(dummyRepository.findCombinedAggregateViewHourly(team, startTime, endTime));
    }

}
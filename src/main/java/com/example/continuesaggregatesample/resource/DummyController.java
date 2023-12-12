package com.example.continuesaggregatesample.resource;

import com.example.continuesaggregatesample.dummy.DummyRepository;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aggregated-dummies")
public class DummyController {

    private final DummyRepository dummyRepository;

    @GetMapping
    public ResponseEntity<Page<?>> getAggregatedDummies(
            @RequestParam String team,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(defaultValue = "5min") String type,
            @RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "10") String count,
            @RequestParam(defaultValue = "bucket") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortType) {

        Instant startTime = Instant.parse(start);
        Instant endTime = Instant.parse(end);
        Pageable paging = PageRequest.of(
                Integer.parseInt(page) - 1, Integer.parseInt(count),
                sortType.equals("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        if (type.equals("hourly"))
            return ResponseEntity.ok(dummyRepository.findCombinedAggregateViewHourly(team, startTime, endTime, paging));
        else
            return ResponseEntity.ok(dummyRepository.findCombinedAggregateView(team, startTime, endTime, paging));
    }

}
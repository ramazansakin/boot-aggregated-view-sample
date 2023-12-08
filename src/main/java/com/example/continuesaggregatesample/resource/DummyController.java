package com.example.continuesaggregatesample.resource;

import com.example.continuesaggregatesample.dummy.CombinedAggregateView;
import com.example.continuesaggregatesample.dummy.DummyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aggregated-dummies")
public class DummyController {

    private final DummyRepository dummyRepository;

    @GetMapping
    public ResponseEntity<List<CombinedAggregateView>> getAggregatedDummies(
            @RequestParam String team,
            @RequestParam String start,
            @RequestParam String end) {

        return ResponseEntity.ok(dummyRepository.getCombinedAggregateView(team, start, end));
    }

}
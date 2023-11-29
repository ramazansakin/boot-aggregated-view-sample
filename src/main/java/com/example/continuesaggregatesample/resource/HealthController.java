package com.example.continuesaggregatesample.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/healthz")
    public ResponseEntity<String> healthz() {
        return ResponseEntity.ok("Application is UP & Running!");
    }

}
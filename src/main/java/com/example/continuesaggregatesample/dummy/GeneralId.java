package com.example.continuesaggregatesample.dummy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralId implements Serializable {
    private Long id;
    private Instant time;
}

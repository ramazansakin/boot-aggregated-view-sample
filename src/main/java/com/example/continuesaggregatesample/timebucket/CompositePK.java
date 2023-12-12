package com.example.continuesaggregatesample.timebucket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompositePK implements Serializable {
    private Long id;
    private Long time;
}


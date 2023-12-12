package com.example.continuesaggregatesample.timebucket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeLongTestRepository extends JpaRepository<TimeLongTest, Long> {
}

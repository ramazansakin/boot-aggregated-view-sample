package com.example.continuesaggregatesample.condition;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface ConditionRepository extends JpaRepository<Conditions, Timestamp> {
}


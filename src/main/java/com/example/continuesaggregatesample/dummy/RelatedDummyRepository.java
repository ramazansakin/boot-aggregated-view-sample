package com.example.continuesaggregatesample.dummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RelatedDummyRepository extends JpaRepository<RelatedDummy, Long> {
}

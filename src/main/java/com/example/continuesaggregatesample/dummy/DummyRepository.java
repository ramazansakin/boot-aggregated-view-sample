package com.example.continuesaggregatesample.dummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DummyRepository extends JpaRepository<Dummy, Integer> {

    @Query(value = "SELECT * FROM Dummy d ORDER BY d.time DESC LIMIT 3", nativeQuery = true)
    List<Dummy> findLastThreeRows();

}

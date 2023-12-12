package com.example.continuesaggregatesample.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class RandomConditionGenerator {

    @Autowired
    private ConditionRepository conditionRepository;

    private final List<String> devices = Arrays.asList("deviceA", "deviceB", "deviceC", "deviceD");
    private final List<String> locations = Arrays.asList("Angara", "İstanbul", "Afyon", "Malatya", "Aydın", "Mugla");

    private static AtomicLong idSequence = new AtomicLong(0);

    // TODO - Dummy Data Generator disabled
//    @Scheduled(fixedRate = 60000) // Run every minute
    public void generateDummyData() {

        log.info("###### Random Conditions Data Generator is RUNNING! ######");
        // Generate random data for each device
        for (String device : devices) {
            Conditions condition = new Conditions();
            condition.setSerialid(idSequence.incrementAndGet());
            condition.setTime(new Timestamp(System.currentTimeMillis()));
            condition.setLocation(selectRandomLocation()); // Set your location here
            condition.setDevice(device);
            condition.setTemperature(generateRandomDouble());
            condition.setHumidity(generateRandomDouble());
            condition.setTestone(generateRandomInteger());
            condition.setTesttwo(generateRandomInteger());

            conditionRepository.save(condition);
        }
    }

    private String selectRandomLocation() {
        // Select a random location from the list
        int randomIndex = new Random().nextInt(locations.size());
        return locations.get(randomIndex);
    }

    private double generateRandomDouble() {
        // Generate a random double value between 0 and 100
        return new Random().nextDouble() * 100;
    }

    private int generateRandomInteger() {
        // Generate a random integer value between 0 and 100
        return new Random().nextInt(101);
    }
}
package com.example.continuesaggregatesample.dummy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Random;


@Slf4j
@Component
public class DummyDataGenerator {

    @Autowired
    private DummyRepository dummyRepository;

    @Scheduled(fixedRate = 60000)
    public void generateDummyData() {
        log.info("###### Random Dummy Data Generator is RUNNING! ######");

        String[] teams = {"teamX", "teamY", "teamZ"};
        for (String team : teams) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            int testone = new Random().nextInt(0, 10);
            long testtwo = new Random().nextInt(10);
            double testthree = new Random().nextDouble(10);

            Dummy dummy = new Dummy();
            dummy.setTimestamp(timestamp);
            dummy.setTeam(team);
            dummy.setTestone(testone);
            dummy.setTesttwo(testtwo);
            dummy.setTestthree(testthree);

            dummyRepository.save(dummy);
        }
    }

}
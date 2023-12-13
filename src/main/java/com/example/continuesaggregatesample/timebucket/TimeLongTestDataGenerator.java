package com.example.continuesaggregatesample.timebucket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimeLongTestDataGenerator {

    private final TimeLongTestRepository timeLongTestRepository;

    @Scheduled(fixedRate = 60000)
    public void generateDummyData() {
        log.info("###### TimeLongTest Data Generator Job is RUNNING! ######");

        String[] groups = {"groupA", "groupB", "groupC", "groupD"};
        for (String group : groups) {

            // Set milliseconds format Epoch-time
            Long timestamp = Instant.now().getEpochSecond() * 1000;
            int test = new Random().nextInt(10);

            TimeLongTest timeLongTest = new TimeLongTest();
            timeLongTest.setTime(timestamp);
            timeLongTest.setGroupName(group);
            timeLongTest.setTest(test);

            timeLongTestRepository.save(timeLongTest);
        }
    }

}
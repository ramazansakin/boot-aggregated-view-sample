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
    public void generateTimeLongTestData() {
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
            timeLongTest.setSample(generateRandomString(3));
            timeLongTest.setLastseentime(Instant.now().getEpochSecond());

            timeLongTestRepository.save(timeLongTest);
        }
    }

    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder randomStringBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomStringBuilder.append(randomChar);
        }

        return randomStringBuilder.toString();
    }

}
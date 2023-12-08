package com.example.continuesaggregatesample.dummy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Random;


@Slf4j
@Component
@RequiredArgsConstructor
public class DummyDataGenerator {

    private final DummyRepository dummyRepository;
    private final RelatedDummyRepository relatedDummyRepository;


    @Scheduled(fixedRate = 60000)
    public void generateDummyData() {
        log.info("###### Dummy Data Generator Job is RUNNING! ######");

        String[] teams = {"teamX", "teamY", "teamZ"};
        for (String team : teams) {

            Instant timestamp = Instant.now();
            int testone = new Random().nextInt(0, 10);
            long testtwo = new Random().nextInt(10);
            double testthree = new Random().nextDouble(10);

            Dummy dummy = new Dummy();
            dummy.setTime(timestamp);
            dummy.setTeam(team);
            dummy.setTestone(testone);
            dummy.setTesttwo(testtwo);
            dummy.setTestthree(testthree);

            dummyRepository.save(dummy);
        }
    }


    // @Deprecated Runs every 1 mins with 10 secs delay to wait for new dummies to relate
    // Generating same team related values to be able to join with dummies at the same time
    @Scheduled(fixedRate = 60000)
    public void generateRelatedDummyData() {
        log.info("###### Related Dummy Data Generator Job is RUNNING! ######");

//        List<Dummy> lastThreeRows = dummyRepository.findLastThreeRows();

        List.of("teamX", "teamY", "teamZ").forEach(team -> {
            Instant timestamp = Instant.now();
            int othertestone = new Random().nextInt(10, 50);
            int othertesttwo = new Random().nextInt(20);

            RelatedDummy relatedDummy = new RelatedDummy();
            relatedDummy.setTime(timestamp);
            relatedDummy.setTeam(team);
            relatedDummy.setOthertestone(othertestone);
            relatedDummy.setOthertesttwo(othertesttwo);

            relatedDummyRepository.save(relatedDummy);
        });

    }

}
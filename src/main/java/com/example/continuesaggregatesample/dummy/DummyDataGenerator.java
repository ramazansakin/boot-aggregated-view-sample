package com.example.continuesaggregatesample.dummy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;


@Slf4j
@Component
public class DummyDataGenerator {

    @Autowired
    private DummyRepository dummyRepository;

    //    @Scheduled(fixedRate = 60000)
    public void generateDummyData() {

        log.info("###### Random Dummy Data Generator is RUNNING! ######");

        String[] cids = {"12:12:12:12", "23:23:23:23", "34:34:34:34", "45:45:45:45", "56:56:56:56"};
        for (String cid : cids) {
            long timestamp = System.currentTimeMillis();
            int testone = new Random().nextInt(0, 100);
            long testtwo = new Random().nextInt(10000);
            double testthree = new Random().nextDouble() * 100;

            Dummy dummy = new Dummy();
            dummy.setTimestamp(timestamp);
            dummy.setCid(cid);
            dummy.setTestone(testone);
            dummy.setTesttwo(testtwo);
            dummy.setTestthree(testthree);

            dummyRepository.save(dummy);

        }
    }

}
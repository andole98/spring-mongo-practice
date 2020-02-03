package com.andole.mongo;

import com.andole.mongo.domain.Person;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class Mongo {
    private static final Logger log = getLogger(Mongo.class);
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate = new MongoTemplate(MongoClients.create(), "database");
    }

    @Test
    void insert() {
        clockWatch(this::insertOne);
        clockWatch(this::collections);
    }

    private void clockWatch(Runnable task) {
        long start = System.currentTimeMillis();
        task.run();
        long end = System.currentTimeMillis();
        log.error("duration: {}", end - start);
    }

    void insertOne() {
        for (int i = 0; i < 10000; i++) {
            mongoTemplate.insert(new Person("andole", i));
        }
    }

    void collections() {
        List<Person> idbb = IntStream.range(0, 10000)
                .mapToObj(i -> new Person("idbb", i))
                .collect(Collectors.toList());
        mongoTemplate.insertAll(idbb);
    }

    @Test
    void find() {
        mongoTemplate.find(new Query(where("name").is("idbb")), Person.class);
    }
}

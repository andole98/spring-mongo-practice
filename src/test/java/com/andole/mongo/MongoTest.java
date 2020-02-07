package com.andole.mongo;

import com.andole.mongo.domain.MongoPerson;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoTest {
    private static final Logger log = getLogger(MongoTest.class);
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate = new MongoTemplate(MongoClients.create("mongodb://10.113.92.223"), "database");
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
            mongoTemplate.insert(new MongoPerson("andole"));
        }
    }

    void collections() {
        List<MongoPerson> idbb = IntStream.range(0, 10000)
                .mapToObj(i -> new MongoPerson("idbb"))
                .collect(Collectors.toList());
        mongoTemplate.insertAll(idbb);
    }

    @Test
    void find() {
        clockWatch(this::findWithoutIndex);
        mongoTemplate.indexOps(MongoPerson.class).ensureIndex(new Index("number", Sort.Direction.ASC));
        clockWatch(this::findWithIndex);
    }

    private void findWithoutIndex() {
        mongoTemplate.find(new Query(where("number").is(9898)), MongoPerson.class);
    }

    private void findWithIndex() {
        mongoTemplate.find(new Query(where("number").is(9898)), MongoPerson.class);
    }
}

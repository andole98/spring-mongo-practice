package com.andole.mongo;

import com.andole.mongo.domain.Person;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class Mongo {
    private static final Logger log = getLogger(Mongo.class);

    @Test
    void insertAndFind() {
        MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "database");

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            log.info(mongoOps.insert(new Person("andole", i)).toString());
        }

        long end = System.currentTimeMillis();
        log.error("duration: {}", end - start);
        log.info(mongoOps.findOne(new Query(where("name").is("andole")), Person.class).toString());
    }
}

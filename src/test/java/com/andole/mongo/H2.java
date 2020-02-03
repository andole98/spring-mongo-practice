package com.andole.mongo;

import com.andole.mongo.domain.JpaPerson;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.slf4j.LoggerFactory.getLogger;

@DataJpaTest
public class H2 {
    private static final Logger log = getLogger(H2.class);
    @Autowired
    private TestEntityManager tm;

    @Test
    void h2() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            log.info(tm.persist(new JpaPerson("andole", i)).toString());
        }

        long end = System.currentTimeMillis();
        log.error("duration: {}", end - start);
    }
}

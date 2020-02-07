package com.andole.mongo;

import com.andole.mongo.domain.Merchant;
import com.andole.mongo.domain.Trade;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
public class MongoRemoteTest {
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate = new MongoTemplate(MongoClients.create(System.getenv("MONGO")), "test");
    }

    @Test
    void insert() {
        for (int i = 0; i < 100; i++) {
            List<Trade> trades = new ArrayList<>(100000);
            mongoTemplate.insertAll(IntStream.range(0, 10000)
                    .mapToObj(integer -> new Merchant(UUID.randomUUID().toString(), String.valueOf(integer), 0))
                    .peek(merchant -> {
                                for (int j = 0; j < 10; j++) {
                                    trades.add(new Trade(UUID.randomUUID().toString(), merchant.getId(), BigDecimal.valueOf(10000), "1"));
                                }
                            }
                    )
                    .collect(Collectors.toList()));
            mongoTemplate.insertAll(trades);
        }
    }

    @Test
    void findOne() {
        mongoTemplate.find(new Query(where("_id").is("something")), Merchant.class);

        //operation: 508, total: 536
        log.error("non-index-query");
        clockWatch(this::findByName);

        // operation: 7, total: 9
        log.error("use-pk");
        clockWatch(this::findByPK);

        mongoTemplate.indexOps(Merchant.class).ensureIndex(new Index("name", Sort.Direction.ASC).named("IX_NAME"));

        // operation: 10, total: 22
        log.error("use-index");
        clockWatch(this::findByName);

        mongoTemplate.indexOps(Merchant.class).dropIndex("IX_NAME");
    }

    private void findByPK() {
        List<Merchant> merchants = mongoTemplate.find(new Query(where("_id").is("1b2e6b8d-88e0-4252-b679-4d18d131a450")), Merchant.class);
        System.out.println(merchants);
    }

    private void findByName() {
        List<Merchant> name = mongoTemplate.find(new Query(where("name").is("8721")), Merchant.class);
        System.out.println(name);
    }

    private void clockWatch(Runnable task) {
        long start = System.currentTimeMillis();
        task.run();
        long end = System.currentTimeMillis();
        log.error(String.valueOf(end - start));
    }
}

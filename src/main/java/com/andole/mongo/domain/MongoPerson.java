package com.andole.mongo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bson.types.ObjectId;

@EqualsAndHashCode(of = "id")
@Getter
public class MongoPerson {
    private ObjectId id;
    private String name;

    public MongoPerson(String name) {
        this.name = name;
    }
}
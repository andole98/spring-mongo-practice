package com.andole.mongo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Merchant {
    private String id;
    private String name;
    private Integer creditGrade;
}

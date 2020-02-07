package com.andole.mongo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Trade {
    private String id;
    private String merchantId;
    private BigDecimal amount;
    private String type;
}

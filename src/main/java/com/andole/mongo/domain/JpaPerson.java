package com.andole.mongo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;


@Getter
@EqualsAndHashCode(of = "id")
@Entity
@Table(indexes = @Index(columnList = "name"))
public class JpaPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public JpaPerson(String name) {
        this.name = name;
    }
}

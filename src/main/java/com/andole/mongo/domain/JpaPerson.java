package com.andole.mongo.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(indexes = @Index(columnList = "name,number"))
public class JpaPerson extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public JpaPerson(String name, int number) {
        super(name, number);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaPerson jpaPerson = (JpaPerson) o;
        return Objects.equals(id, jpaPerson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

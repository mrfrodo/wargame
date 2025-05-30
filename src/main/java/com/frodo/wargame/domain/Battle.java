package com.frodo.wargame.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("battles")
public record Battle(
        @Id
        @Column("id") // lowercase to match schema
        Long id,

        @Column("name")
        String name,

        @Column("battleyear")
        Integer battleyear
) {}
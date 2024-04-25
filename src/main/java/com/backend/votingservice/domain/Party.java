package com.backend.votingservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table("party")
public class Party {
    @PrimaryKey
    private UUID id = UUID.randomUUID(); // Automatically generate a UUID

    @Column("name")
    private String name;

    @Column("member_ids")
    private Set<Long> memberIds = new HashSet<>();

    @Column("movie_external_ids")
    private Set<String> movieExternalIds = new HashSet<>();
}


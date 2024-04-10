package com.backend.votingservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "party_members", joinColumns = @JoinColumn(name = "party_id"))
    @Column(name = "user_id")
    private Set<Long> memberIds = new HashSet<>();

    // storing external movie IDs instead of direct Movie entity references
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "party_movie_suggestions", joinColumns = @JoinColumn(name = "party_id"))
    @Column(name = "movie_external_id")
    private Set<String> movieExternalIds = new HashSet<>();
}

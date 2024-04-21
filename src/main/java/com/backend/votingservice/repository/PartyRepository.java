package com.backend.votingservice.repository;

import com.backend.votingservice.domain.Party;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import java.util.Optional;

public interface PartyRepository  extends CassandraRepository<Party, Long> {

    // WARNING: Using ALLOW FILTERING is generally not recommended for production systems as it can lead to severe performance issues due to full table scans, especially as data grows. This is a temporary solution to enable functionality but should be revisited.
    /* TODO: Reconsider the data model for the 'member_ids' usage. Ideally, restructure the database schema to avoid the need for data filtering in queries.
             This might involve denormalizing data or creating additional tables specifically structured to support efficient queries for membership checks.
             Consult data modeling best practices for Cassandra and consider re-designing the schema to improve query performance and scalability.*/
    @Query("SELECT * FROM party WHERE member_ids contains :userId ALLOW FILTERING")
    Optional<Party> findPartyByMemberId(Long userId);

}

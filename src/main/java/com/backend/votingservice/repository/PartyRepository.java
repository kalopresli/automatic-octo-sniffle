package com.backend.votingservice.repository;

import com.backend.votingservice.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PartyRepository  extends JpaRepository<Party, Long> {
    @Query("SELECT p FROM Party p JOIN p.memberIds memberId WHERE memberId = :userId")
    Optional<Party> findPartyByMemberId(@Param("userId") Long userId);

}

package com.backend.votingservice.service;

import com.backend.votingservice.domain.Party;
import com.backend.votingservice.dto.UserFavoriteMoviesMessage;
import com.backend.votingservice.repository.PartyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;


    @Transactional
    public void processFavoriteMoviesMessage(UserFavoriteMoviesMessage message) {
        Party party = findOrCreatePartyForUser(message.getUserId());
        party.getMovieExternalIds().addAll(message.getFavoriteMovieIds());
        partyRepository.save(party);
    }

    @Transactional
    private Party findOrCreatePartyForUser(Long userId) {
        return partyRepository.findPartyByMemberId(userId)
                .orElseGet(() -> {
                    Party newParty = new Party();
                    newParty.setName("New Watch Party By " + userId);
                    newParty.getMemberIds().add(userId);
                    return partyRepository.save(newParty);
                });
    }

    @Transactional
    public Party getParty(Long userId) throws Exception {
        return partyRepository.findPartyByMemberId(userId).orElseThrow(() -> new Exception("User not found - " + userId));
    }
}
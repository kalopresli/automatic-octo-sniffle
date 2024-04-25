package com.backend.votingservice.service;

import com.backend.votingservice.domain.Party;
import com.backend.votingservice.dto.UserFavoriteMoviesMessage;
import com.backend.votingservice.repository.PartyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PartyService {

    private final PartyRepository partyRepository;



    public void processFavoriteMoviesMessage(UserFavoriteMoviesMessage message) {
        Party party = partyRepository.findPartyByMemberId(message.getUserId())
                .orElse(new Party()); // Create a new Party if not found
        party.getMovieExternalIds().addAll(message.getFavoriteMovieIds());
        partyRepository.save(party);
    }



    private Party findOrCreatePartyForUser(Long userId) {
        return partyRepository.findPartyByMemberId(userId)
                .orElseGet(() -> {
                    Party newParty = new Party();
                    newParty.setName("New Watch Party By " + userId);
                    newParty.getMemberIds().add(userId);
                    return partyRepository.save(newParty);
                });
    }


    public Party getParty(Long userId) throws Exception {
        return partyRepository.findPartyByMemberId(userId).orElseThrow(() -> new Exception("User not found - " + userId));
    }
}
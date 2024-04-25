package com.backend.votingservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.backend.votingservice.domain.Party;
import com.backend.votingservice.dto.UserFavoriteMoviesMessage;
import com.backend.votingservice.repository.PartyRepository;
import com.backend.votingservice.service.PartyService;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PartyServiceTests {

    @Mock
    private PartyRepository partyRepository;

    @InjectMocks
    private PartyService partyService;

    private Party setupParty() {
        Party party = new Party();
        party.setMovieExternalIds(new HashSet<>()); // Ensure this is initialized
        return party;
    }

    @Test
    public void whenPartyExists_addFavoriteMovies() {
        Party party = setupParty();
        UserFavoriteMoviesMessage message = new UserFavoriteMoviesMessage(1L, new HashSet<>(List.of("movie1", "movie2")));

        when(partyRepository.findPartyByMemberId(1L)).thenReturn(Optional.of(party));

        partyService.processFavoriteMoviesMessage(message);

        Assertions.assertThat(party.getMovieExternalIds())
                .as("Check that all favorite movie IDs are added")
                .containsAll(message.getFavoriteMovieIds());

        verify(partyRepository).save(party);
    }

    @Test
    public void whenPartyDoesNotExist_createAndAddFavoriteMovies() {
        UserFavoriteMoviesMessage message = new UserFavoriteMoviesMessage(2L, new HashSet<>(List.of("movie3")));
        when(partyRepository.findPartyByMemberId(2L)).thenReturn(Optional.empty());
        when(partyRepository.save(any(Party.class))).thenAnswer(invocation -> invocation.getArgument(0));

        partyService.processFavoriteMoviesMessage(message);

        verify(partyRepository).save(any(Party.class));
    }

    @Test
    public void whenPartyDoesNotExist_getPartyThrowsException() {
        Long userId = 1L;
        when(partyRepository.findPartyByMemberId(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> partyService.getParty(userId))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("User not found");

        verify(partyRepository).findPartyByMemberId(userId);
    }

    @Test
    public void whenPartyExists_getPartyDoesNotThrowException() {
        Long userId = 1L;  // Using Long for userId
        Party expectedParty = new Party();
        expectedParty.setId(UUID.randomUUID());  // Setting UUID for party's ID

        when(partyRepository.findPartyByMemberId(userId)).thenReturn(Optional.of(expectedParty));

        assertThatCode(() -> {
            Party result = partyService.getParty(userId);
            assertThat(result).isEqualTo(expectedParty);
        }).doesNotThrowAnyException();

        verify(partyRepository).findPartyByMemberId(userId);
    }

}
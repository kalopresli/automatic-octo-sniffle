package com.backend.votingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoriteMoviesMessage {
    private Long userId;
    private Set<String> favoriteMovieIds; // ids of the user's favorite movies
}

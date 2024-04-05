package com.example.movie_server.service;

import com.example.movie_server.model.*;
import com.example.movie_server.repository.MovieRepository;
import com.example.movie_server.repository.ActorRepository;
import com.example.movie_server.repository.WatchlistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTests {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private WatchlistRepository watchlistRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    @DisplayName("Test getAll()")
    public void getAllTest() {
        // Given
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());

        when(movieRepository.findAll()).thenReturn(movies);

        // When
        List<Movie> result = movieService.getAll();

        // Then
        assertThat(result).hasSize(2);
        assertEquals(result, movies);
    }

    @Test
    @DisplayName("Test getMovieById()")
    public void getMovieByIdTest() {
        // Given
        Movie movie = new Movie();
        movie.setId("1");

        when(movieRepository.findById("1")).thenReturn(Optional.of(movie));

        // When
        Movie result = movieService.getMovieById("1");

        // Then
        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    @DisplayName("Test getMovieActors()")
    public void getMovieActorsTest() {
        // Given
        Movie movie = new Movie();
        List<ActorRole> roles = new ArrayList<>();
        roles.add(ActorRole.builder().actorId("1").build());
        roles.add(ActorRole.builder().actorId("2").build());
        movie.setRoles(roles);
        movie.setId("001");

        Actor actor1 = new Actor();
        actor1.setId("1");
        Actor actor2 = new Actor();
        actor2.setId("2");

        when(movieRepository.findById("001")).thenReturn(Optional.of(movie));
        when(actorRepository.findById("1")).thenReturn(Optional.of(actor1));
        when(actorRepository.findById("2")).thenReturn(Optional.of(actor2));

        // When
        List<Actor> result = movieService.getMovieActors("001");

        // Then
        assertThat(result).hasSize(2);
    }

}

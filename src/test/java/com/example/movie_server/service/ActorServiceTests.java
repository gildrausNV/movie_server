package com.example.movie_server.service;

import com.example.movie_server.model.Actor;
import com.example.movie_server.model.ActorRole;
import com.example.movie_server.model.Movie;
import com.example.movie_server.repository.ActorRepository;
import com.example.movie_server.repository.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

//import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActorServiceTests {
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private ActorService actorService;
    @InjectMocks
    private MovieService movieService;

    @Test
    @DisplayName("Test saveActor()")
    public void saveActorTest(){
        Actor actor = Actor.builder()
                .firstName("test")
                .dateOfBirth("test")
                .lastName("test")
                .nationality("test")
                .placeOfBirth("test")
                .build();

        when(actorRepository.save(actor)).thenReturn(actor);

        Actor result = actorService.saveActor(actor);

        verify(actorRepository, times(1)).save(actor);
        assertEquals(result, actor);
    }

    @Test
    @DisplayName("Test saveActor() with empty fields")
    public void saveActorTestWithEmptyFields() {
        // Create an actor with empty fields
        Actor actor = Actor.builder()
                .firstName("")
                .nationality("")
                .placeOfBirth("")
                .dateOfBirth("")
                .lastName("")
                .build();

        // Call the saveActor method and assert that it throws an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> actorService.saveActor(actor));
    }

    @Test
    @DisplayName("Test findAll()")
    public void findAllTest(){
        List<Actor> actors = new ArrayList<>();
        actors.add(Actor.builder()
                .firstName("test")
                .dateOfBirth("test")
                .lastName("test")
                .nationality("test")
                .placeOfBirth("test")
                .build());
        actors.add(Actor.builder()
                .firstName("test2")
                .dateOfBirth("test2")
                .lastName("test2")
                .nationality("test2")
                .placeOfBirth("test2")
                .build());

        Page<Actor> actorPage = mock(Page.class);
        when(actorRepository.findAll(any(PageRequest.class))).thenReturn(actorPage);
        when(actorPage.getContent()).thenReturn(actors);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Actor> result = actorService.getAllActors(pageable);

        assertEquals(result.getContent(), actors);
        verify(actorRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test getActorMovies")
    public void testGetActorMovies() {

        Movie movie1 = Movie.builder().id("1").title("Movie 1").roles(Arrays.asList(ActorRole.builder().actorId("1").build())).build();
        Movie movie2 = Movie.builder().id("2").title("Movie 2").roles(Arrays.asList(ActorRole.builder().actorId("2").build(), ActorRole.builder().actorId("3").build())).build();
        Movie movie3 = Movie.builder().id("3").title("Movie 3").roles(Arrays.asList(ActorRole.builder().actorId("1").build(), ActorRole.builder().actorId("4").build())).build();

        // Mock the findAll method of movieRepository to return the sample movies
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2, movie3));

        // Call the getActorMovies method with an actorId
        List<Movie> actorMovies = actorService.getActorMovies("1");

        // Check if the correct movies are returned
        List<Movie> expectedMovies = Arrays.asList(movie1, movie3);
        assertEquals(expectedMovies.size(), actorMovies.size());
        for (int i = 0; i < expectedMovies.size(); i++) {
            assertEquals(expectedMovies.get(i).getId(), actorMovies.get(i).getId());
        }
    }

    @Test
    @DisplayName("Test findById()")
    public void findByIdTest(){
        Actor actor = Actor.builder()
                .id("001")
                .firstName("test")
                .lastName("test")
                .placeOfBirth("test")
                .dateOfBirth("test")
                .nationality("test")
                .id("test")
                .build();
        when(actorRepository.findById("001")).thenReturn(Optional.of(actor));

        Actor result = actorService.getActorById("001");

        assertEquals(result, actor);
        verify(actorRepository, times(1)).findById("001");
    }

    @Test
    @DisplayName("Test getActorById() with non-existent ID")
    public void findByIdTestWithNonExistendId(){
        assertThrows(NoSuchElementException.class, () -> actorService.getActorById("001"));
    }
}

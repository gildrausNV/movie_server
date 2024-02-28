package com.example.movie_server.controller;

import com.example.movie_server.model.Actor;
import com.example.movie_server.model.Movie;
import com.example.movie_server.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
@CrossOrigin
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public Page<Actor> getAllActors(Pageable pageable){
        return actorService.getAllActors(pageable);
    }

    @GetMapping("/{actorId}")
    public Actor getActorById(@PathVariable String actorId){
        return actorService.getActorById(actorId);
    }

    @PostMapping
    public Actor saveActor(@RequestBody Actor actor){
        return actorService.saveActor(actor);
    }
    @GetMapping("/search/{firstName}")
    public List<Actor> searchActors(@PathVariable String firstName){
        return actorService.searchActors(firstName);
    }

    @GetMapping("/movies/{actorId}")
    public List<Movie> getActorMovies(@PathVariable String actorId){
        return actorService.getActorMovies(actorId);
    }
}

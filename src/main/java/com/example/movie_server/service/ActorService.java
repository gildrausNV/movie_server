package com.example.movie_server.service;

import com.example.movie_server.model.Actor;
import com.example.movie_server.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;

    public Page<Actor> getAllActors(Pageable pageable){
        return actorRepository.findAll(pageable);
    }

    public Actor getActorById(String actorId) {
        return actorRepository.findById(actorId).orElseThrow(NoSuchElementException::new);
    }

    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Actor> saveActors(List<Actor> actors) {
        List<Actor> currentActors = actorRepository.findAll();
        for (Actor actor: actors){
            if(!currentActors.contains(actor)){
                actorRepository.save(actor);
            }
        }
        return actorRepository.findAll();
    }

    public List<Actor> searchActors(String firstName) {
        return actorRepository.findActorByFirstName(firstName);
    }
}

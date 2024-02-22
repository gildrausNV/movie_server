package com.example.movie_server.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ActorRole {
    private String actorId;
    private String role;
}

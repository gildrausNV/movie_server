package com.example.movie_server.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ActorRole {
    @Id
    private String id;
    private String actorId;
    private String role;

}

package com.example.movie_server.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "movie")
public class Movie {
    @Id
    private String id;
    private String title;
    private String releaseDate;
    private String description;
    private String image;
    private List<ActorRole> roles;
    private Genre genre;
}

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
@Document(collection = "watchlist")
public class Watchlist {
    @Id
    private String id;
    private User user;
    private List<Movie> movies;

}

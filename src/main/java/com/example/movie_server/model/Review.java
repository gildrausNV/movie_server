package com.example.movie_server.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "review")
public class Review {
    @Id
    private String id;
    private Movie movie;
    private User user;
    private String content;
    private int rating;
}

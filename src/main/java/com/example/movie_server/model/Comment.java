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
@Document(collection = "comment")
public class Comment {
    @Id
    private String id;
    private Movie movie;
    private User user;
    private String content;

    public Comment(Movie movie, User currentlyLoggedInUser, String content) {
        this.movie = movie;
        this.user = currentlyLoggedInUser;
        this.content = content;
    }
}

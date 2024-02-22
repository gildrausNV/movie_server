package com.example.movie_server.auth;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LoginRequest {
    private String username;
    private String password;
}

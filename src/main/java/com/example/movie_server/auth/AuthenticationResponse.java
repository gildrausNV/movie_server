package com.example.movie_server.auth;

import com.example.movie_server.model.Role;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthenticationResponse {
    private String token;
    private Role role;
    private String id;
}

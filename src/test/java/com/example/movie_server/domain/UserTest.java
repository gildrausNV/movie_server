package com.example.movie_server.domain;

import com.example.movie_server.model.Role;
import com.example.movie_server.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    @DisplayName("Test User construction with builder")
    public void testUserBuilder() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .username("johndoe")
                .password("password")
                .role(Role.USER)
                .avatar("avatar.jpg")
                .build();

        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(Role.USER, user.getRole());
        assertEquals("avatar.jpg", user.getAvatar());
    }

    @Test
    @DisplayName("Test User construction with constructor")
    public void testUserConstructor() {
        User user = new User("John", "Doe", "john.doe@example.com", "johndoe", "password", Role.USER, "avatar.jpg");

        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("johndoe", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(Role.USER, user.getRole());
        assertEquals("avatar.jpg", user.getAvatar());
    }

}

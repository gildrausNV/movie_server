package com.example.movie_server.service;

import com.example.movie_server.model.Role;
import com.example.movie_server.model.User;
import com.example.movie_server.repository.UserRepository;
import com.example.movie_server.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Test saveUser()")
    void saveUserTest() {
        User user = User.builder()
                .lastName("test")
                .email("test@gmail.com")
                .firstName("test")
                .password("test")
                .username("test")
                .role(Role.USER)
                .build();

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        verify(userRepository, times(1)).save(user);
        assertEquals(user, result);
    }

    @Test
    @DisplayName("Test saveUser() with empty fields")
    public void saveUserWithEmptyFieldsTest(){
        User user = User.builder()
                .lastName("")
                .email("")
                .firstName("")
                .password("")
                .username("")
                .role(Role.USER)
                .build();
        assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    @DisplayName("Test findAll()")
    public void findAllTest(){
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .lastName("test")
                .email("test@gmail.com")
                .firstName("test")
                .password("test")
                .username("test")
                .role(Role.USER)
                .build());
        users.add(User.builder()
                .lastName("test2")
                .email("test2@gmail.com")
                .firstName("test2")
                .password("test2")
                .username("test2")
                .role(Role.USER)
                .build());
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAll();
        assertThat(result).isEqualTo(users);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test findUserById()")
    public void findUserByIdTest(){
        User user = User.builder()
                .lastName("test")
                .email("test@gmail.com")
                .firstName("test")
                .password("test")
                .username("test")
                .role(Role.USER)
                .id("001")
                .build();

        when(userRepository.findUserById("001")).thenReturn(Optional.of(user));
        User result = userService.getUserById("001");

        verify(userRepository, times(1)).findUserById("001");
        assertEquals(result, user);
    }

    @Test
    @DisplayName("Test findUserById with non-existent ID")
    public void findByUserIdWithNonExistentIdTest(){
        assertThrows(NoSuchElementException.class, () -> userService.getUserById("001"));
    }
}

package com.example.movie_server.auth;

import com.example.movie_server.config.JwtService;
import com.example.movie_server.model.Role;
import com.example.movie_server.model.User;
import com.example.movie_server.repository.UserRepository;
import com.example.movie_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .avatar(request.getAvatar())
                .build();
        userService.save(user);
        userService.createWatchlist(user);

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, Role.USER, user.getId());
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

//        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, user.getRole(), user.getId());
    }


    private String generateRandomVerificationCode() {
        String characters = "0123456789";
        StringBuilder code = new StringBuilder();

        Random random = new Random();
        IntStream.range(0,6).forEach(i -> {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        });

        return code.toString();
    }
}

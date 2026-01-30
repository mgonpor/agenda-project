package com.miguel.auth;

import com.miguel.web.config.JwtService;
import com.miguel.persistence.entities.user.UserRepository;
import com.miguel.services.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = AuthenticationMapper.registerToUser(request);
        user.setPassword(encoder.encode(request.getPassword()));

        repository.save(user);

        var jwtToken = jwtService.generateJwtToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        var jwtToken = jwtService.generateJwtToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}

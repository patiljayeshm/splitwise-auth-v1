package com.splitshare.SignUpService.Services.imp;

import com.splitshare.SignUpService.Model.*;
import com.splitshare.SignUpService.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = UserModel.builder()
                .username(registerRequest.getUsername().trim().toLowerCase())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .lastName(registerRequest.getLastName().trim())
                .firstName(registerRequest.getFirstName().trim())
                .gender(registerRequest.getGender())
                .dateOfBirth(registerRequest.getDateOfBirth())
                .build();

        userRepository.save(user);
        var jwtToken =jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();

    }
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        var user  = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(()-> new UsernameNotFoundException("UserNotFound."));
        var jwtToken =jwtService.generateToken(user);
        var expiry = jwtService.extractExpiration(jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(authenticationRequest.getUsername())
                .validTill(expiry)
                .build();

    }

}

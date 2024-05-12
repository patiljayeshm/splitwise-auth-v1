package com.splitshare.SignUpService.Services.imp;

import com.splitshare.SignUpService.Model.*;
import com.splitshare.SignUpService.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;
    private final StandardPBEStringEncryptor strongEncryptor;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        String encodedPassword =passwordEncoder.encode(registerRequest.getPassword());

        var user = UserModel.builder()
                .username(registerRequest.getUsername().trim().toLowerCase())
                .email(registerRequest.getEmail())
                .password(strongEncryptor.encrypt(encodedPassword))
                .role(Role.USER)
                .lastName(registerRequest.getLastName().trim())
                .firstName(registerRequest.getFirstName().trim())
                .gender(registerRequest.getGender())
                .dateOfBirth(registerRequest.getDateOfBirth())
                .build();

        String decrypt = strongEncryptor.decrypt(strongEncryptor.encrypt(encodedPassword));

        userRepository.save(user);
        var jwtToken =jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();

    }
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("UserNotFound."));
        String decryptedPassword = strongEncryptor.decrypt(user.getPassword());
        log.info("password {}", decryptedPassword);
        if (!passwordEncoder.matches(authenticationRequest.getPassword(), decryptedPassword)) {
            throw new BadCredentialsException("Invalid credentials");
        }
        var jwtToken = jwtService.generateToken(user);
        var expiry = jwtService.extractExpiration(jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(authenticationRequest.getUsername())
                .validTill(expiry)
                .build();
    }
}

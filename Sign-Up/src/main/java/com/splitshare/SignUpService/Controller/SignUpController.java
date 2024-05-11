package com.splitshare.SignUpService.Controller;


import com.splitshare.SignUpService.Model.AuthenticationResponse;
import com.splitshare.SignUpService.Model.AuthenticationRequest;
import com.splitshare.SignUpService.Model.RegisterRequest;
import com.splitshare.SignUpService.Services.imp.AuthenticationService;
import com.splitshare.SignUpService.Services.imp.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class SignUpController {
    private final SignUpService signUpService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody RegisterRequest registerRequest)throws Exception{
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody AuthenticationRequest authenticationRequest)throws Exception{
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}

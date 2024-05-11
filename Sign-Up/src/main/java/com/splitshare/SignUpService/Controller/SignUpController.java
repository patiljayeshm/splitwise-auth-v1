package com.splitshare.SignUpService.Controller;


import com.splitshare.SignUpService.Model.UserModel;
import com.splitshare.SignUpService.Services.imp.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signUp(@RequestBody UserModel userModel){
        signUpService.SignUpUser(userModel);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

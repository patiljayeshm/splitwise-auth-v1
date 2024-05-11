package com.splitshare.SignUpService.Services.imp;

import com.splitshare.SignUpService.Model.UserModel;
import com.splitshare.SignUpService.Repository.SignUpRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final SignUpRepo signUpRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void SignUpUser(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
         signUpRepo.save(userModel);
    }
}

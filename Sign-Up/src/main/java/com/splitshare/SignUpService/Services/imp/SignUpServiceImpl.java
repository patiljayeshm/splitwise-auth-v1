package com.splitshare.SignUpService.Services.imp;

import com.splitshare.SignUpService.Model.LogInModelUser;
import com.splitshare.SignUpService.Model.UserModel;
import com.splitshare.SignUpService.Repository.UserRepository;
import com.splitshare.SignUpService.Utils.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EncryptionUtil encryptionUtil;


    @Override
    public void SignUpUser(UserModel userModel) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        try {
            userModel.setPassword(encryptionUtil.encrypt(passwordEncoder.encode(userModel.getPassword())));
            userRepository.save(userModel);
        }catch (Exception e){
            log.info(e.toString());
        }

    }

    @Override
    public ResponseEntity<Boolean> isLogIn(LogInModelUser userDetails) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        boolean isLogInFlag = false;
        if (null!=userDetails){
            Optional<UserModel> user = userRepository.findByUsername(userDetails.getUsername());
            String userPasswordEncoded = encryptionUtil.decrypt(user.get().getPassword());
            if (passwordEncoder.matches(userDetails.getPassword(),userPasswordEncoded)){
                isLogInFlag= true;
            }
        }else {
            throw new RuntimeException("User not found please check details.");
        }
        return ResponseEntity.ok(isLogInFlag) ;
    }
}

package com.ecommerce.shop.service;

import com.ecommerce.shop.dto.ResponseDto;
import com.ecommerce.shop.dto.user.SignInDto;
import com.ecommerce.shop.dto.user.SignInResponseDto;
import com.ecommerce.shop.dto.user.SignupDto;
import com.ecommerce.shop.dto.user.UserCreateDto;
import com.ecommerce.shop.enums.ResponseStatus;
import com.ecommerce.shop.enums.Role;
import com.ecommerce.shop.exceptions.AuthenticationFailException;
import com.ecommerce.shop.exceptions.CustomException;
import com.ecommerce.shop.model.AuthenticationToken;
import com.ecommerce.shop.model.User;
import com.ecommerce.shop.repository.UserRepository;
import com.ecommerce.shop.utils.Helper;
import com.ecommerce.shop.utils.MessageStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.ecommerce.shop.utils.MessageStrings.USER_CREATED;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(UserService.class);


    public ResponseDto signUp(SignupDto signupDto)  throws CustomException {

        if (Helper.notNull(userRepository.findByEmail(signupDto.getEmail()))) {

            throw new CustomException("User already exists");
        }

        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }


        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), Role.user, encryptedPassword );

        User createdUser;
        try {

            createdUser = userRepository.save(user);

            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);

            authenticationService.saveConfirmationToken(authenticationToken);

            return new ResponseDto(ResponseStatus.success.toString(), USER_CREATED);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws CustomException {

        User user = userRepository.findByEmail(signInDto.getEmail());
        if(!Helper.notNull(user)){
            throw  new AuthenticationFailException("user not present");
        }
        try {

            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){

                throw  new AuthenticationFailException(MessageStrings.WRONG_PASSWORD);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if(!Helper.notNull(token)) {

            throw new CustomException("token not present");
        }

        return new SignInResponseDto ("success", token.getToken());
    }


    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }



}

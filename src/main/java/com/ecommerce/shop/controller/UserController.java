package com.ecommerce.shop.controller;


import com.ecommerce.shop.dto.ResponseDto;
import com.ecommerce.shop.dto.user.SignInDto;
import com.ecommerce.shop.dto.user.SignInResponseDto;
import com.ecommerce.shop.dto.user.SignupDto;
import com.ecommerce.shop.exceptions.AuthenticationFailException;
import com.ecommerce.shop.exceptions.CustomException;
import com.ecommerce.shop.model.User;
import com.ecommerce.shop.repository.UserRepository;
import com.ecommerce.shop.service.AuthenticationService;
import com.ecommerce.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        return userRepository.findAll();
    }

    @PostMapping("/signup")
    public ResponseDto Signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }


    @PostMapping("/signIn")
    public SignInResponseDto Signup(@RequestBody SignInDto signInDto) throws CustomException {
        return userService.signIn(signInDto);
    }
}

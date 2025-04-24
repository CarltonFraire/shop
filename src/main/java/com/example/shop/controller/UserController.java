package com.example.shop.controller;

import com.example.shop.R;
import com.example.shop.services.UserService;
import com.example.shop.validator.UserLoginDto;
import com.example.shop.validator.UserRegisterDto;
import com.example.shop.validator.VerificationCodeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public R login(@Valid UserLoginDto userLoginDto) throws JsonProcessingException {
        return userService.login(userLoginDto);
    }
    @PutMapping("/register")
    public  R register(@Valid UserRegisterDto userRegisterDto) {
        return  userService.register(userRegisterDto);
    }
    @GetMapping("/getCode")
    public R getCode(@Valid VerificationCodeDto verificationCodeDto) {
        return  userService.verificationCode(verificationCodeDto);
    }



}

package com.mikaelson.taskManager.controller;

import com.mikaelson.taskManager.dto.token.TokenResponse;
import com.mikaelson.taskManager.service.UserService;
import com.mikaelson.taskManager.dto.request.UserLoginRequest;
import com.mikaelson.taskManager.dto.request.UserRegisterRecord;
import com.mikaelson.taskManager.dto.response.UserRegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody UserRegisterRecord userDto){
        UserRegisterResponse user = service.registerUser(userDto);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/user/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest userDto) {
        String token = service.login(userDto);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}

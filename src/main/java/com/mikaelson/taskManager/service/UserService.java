package com.mikaelson.taskManager.service;

import com.mikaelson.taskManager.entity.User;
import com.mikaelson.taskManager.repository.UserRepository;
import com.mikaelson.taskManager.dto.request.UserLoginRequest;
import com.mikaelson.taskManager.dto.request.UserRegisterRecord;
import com.mikaelson.taskManager.dto.response.UserRegisterResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserService(UserRepository repository, AuthenticationManager auth, PasswordEncoder pass, TokenService token){
        this.repository = repository;
        this.authenticationManager = auth;
        this.passwordEncoder = pass;
        this.tokenService = token;
    }

    public String login(UserLoginRequest dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.userLogin(),
                        dto.userPassword()
                )
        );

        return tokenService.generateToken((User) Objects.requireNonNull(authentication.getPrincipal()));
    }

    public UserRegisterResponse registerUser(UserRegisterRecord userDto) {
        if(repository.existsByUserLogin(userDto.login())){
            throw new RuntimeException("Usuario ou senha invalida");
        }
        User user = new User();
        user.setUserLogin(userDto.login());
        user.setName(userDto.userName());
        user.setUserPassword(passwordEncoder.encode(userDto.password()));
        return toResponseUser(repository.save(user));
    }



    private UserRegisterResponse toResponseUser(User user) {
        return new UserRegisterResponse(user.getName(), user.getId());
    }
}

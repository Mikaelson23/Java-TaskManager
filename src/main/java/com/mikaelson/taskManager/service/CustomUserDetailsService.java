package com.mikaelson.taskManager.service;

import com.mikaelson.taskManager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            return repository.findByUserLogin(username);
        }catch(UsernameNotFoundException exception){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
    }
}
package com.example.rakus_blog_backend.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.rakus_blog_backend.domain.LoginUserDetails;
import com.example.rakus_blog_backend.domain.User;
import com.example.rakus_blog_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = Optional.of(userRepository.findByEmail(email));
        return user.map(LoginUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(email));
    }

}

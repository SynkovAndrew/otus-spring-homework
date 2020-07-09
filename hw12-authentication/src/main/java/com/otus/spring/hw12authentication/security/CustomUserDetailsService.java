package com.otus.spring.hw12authentication.security;

import com.otus.spring.hw12authentication.repository.RoleRepository;
import com.otus.spring.hw12authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .map(user -> new UserPrincipal(user, roleRepository.findAllByUsername(username)))
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User \"%s\" hasn't been found", username)
                ));
    }
}

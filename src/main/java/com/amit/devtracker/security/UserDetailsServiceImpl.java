package com.amit.devtracker.security;


import com.amit.devtracker.domain.entities.User;
import com.amit.devtracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// Service that loads user-specific data for authentication
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException ("User not found with email: " + email));
        // Wrap the domain User object in a Spring Security-compatible implementation
        return new UserDetailsImpl(user);
    }
}

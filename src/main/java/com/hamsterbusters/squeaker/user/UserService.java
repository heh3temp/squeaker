package com.hamsterbusters.squeaker.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByNickname(nickname);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),
                user.getPassword(),
                authorities
        );
    }

    public User getUserByNickname(String nickname) {
        Optional<User> userOptional = userRepository.findUserByNickname(nickname);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        return user;
    }

}

package com.hamsterbusters.squeaker.user.squeaker;

import com.hamsterbusters.squeaker.user.User;
import com.hamsterbusters.squeaker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class SqueakerConfiguration {

    private final SqueakerProperties squeakerProperties;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner addSqueaker() {
        return args -> {
            Optional<User> userOptional = userRepository.findUserByNickname(squeakerProperties.getNickname());
            if (userOptional.isEmpty()) {
                User user = modelMapper.map(squeakerProperties, User.class);

                String encodedPassword = passwordEncoder.encode(squeakerProperties.getPassword());
                user.setPassword(encodedPassword);

                user.setJoinDate(LocalDateTime.now());
                user.setLastActivity(LocalDateTime.now());
                user.setActive(true);
                userRepository.save(user);
            }
        };
    }

}

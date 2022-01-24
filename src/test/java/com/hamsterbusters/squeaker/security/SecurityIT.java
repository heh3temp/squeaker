package com.hamsterbusters.squeaker.security;

import com.hamsterbusters.squeaker.jwt.JwtTokenUtil;
import com.hamsterbusters.squeaker.user.User;
import com.hamsterbusters.squeaker.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setNickname("Test");
        user.setEmail("test@mail.com");
        user.setPassword(passwordEncoder.encode("Haslo123!"));
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void properLogin() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "Test",
                                    "password": "Haslo123!"
                                }"""))
                .andExpect(status().isOk());
    }

    @Test
    void invalidPassword() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "Test",
                                    "password": "bad_password"
                                }"""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void invalidUsername() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "badUsername",
                                    "password": "Haslo123!"
                                }"""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void requestWithExpiredToken() throws Exception {
        String token = jwtTokenUtil.getExpiredToken();

        mockMvc.perform(get("/posts")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Expired authorization token"));
    }

    @Test
    void requestWithTokenWithInvalidSignature() throws Exception {
        String token = jwtTokenUtil.getTokenWithInvalidSignature();

        mockMvc.perform(get("/posts")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Could not authorize"));
    }

    @Test
    void requestWithMalformedToken() throws Exception {
        mockMvc.perform(get("/posts")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer TestInvalidToken"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Could not authorize"));
    }

}

package com.hamsterbusters.squeaker.user;

import com.hamsterbusters.squeaker.jwt.JwtTokenUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getUserThatExists() throws Exception {
        //given
        String nickname = "Test";

        User user = new User();
        user.setNickname(nickname);
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);
        user = userRepository.save(user);

        int userId = user.getUserId();

        String token = jwtTokenUtil.getValidToken(userId, nickname);

        // when
        // then
        mockMvc.perform(get(String.format("/user/%d", userId)).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(nickname));
    }

    @Test
    void getUserThatDoesNotExist() throws Exception {
        //given
        int userId = 1;
        String nickname = "Test";

        String token = jwtTokenUtil.getValidToken(userId, nickname);

        // when
        // then
        mockMvc.perform(get(String.format("/user/%d", userId)).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found in the database"));
    }

    @Test
    public void addUserInvalidPassword() throws Exception {

        String userJSON = """
                                {
                                    "nickname": "test",
                                    "email": "test@gmail.com",
                                    "password": "AbaBmk1231"
                                }
                            """;

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Invalid password. Must be between 8 and 100 characters and include small and large letters, digits and special characters"));

    }

    @Test
    public void addUserInvalidNickname() throws Exception {

        String userJSON = """
                                {
                                    "nickname": "test  ",
                                    "email": "test@gmail.com",
                                    "password": "AbaBmk1231&&"
                                }
                            """;

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Invalid nickname. Valid can include letters, digits, underscores and must be between 3 and 40 characters long"));

    }

    @Test
    public void addUserInvalidEmail() throws Exception {

        String userJSON = """
                                {
                                    "nickname": "test",
                                    "email": "..@..",
                                    "password": "AbaBmk1231&&"
                                }
                            """;

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Invalid email"));

    }

    @Test
    public void addUserCorrect() throws Exception {

        String userJSON = """
                                {
                                    "nickname": "test",
                                    "email": "test@gmail.com",
                                    "password": "AbaBmk1231&&"
                                }
                            """;

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJSON))
                .andExpect(status().isOk());
    }

}
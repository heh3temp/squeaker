package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.jwt.JwtTokenUtil;
import com.hamsterbusters.squeaker.user.User;
import com.hamsterbusters.squeaker.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getPostThatExist() throws Exception {
        // given

        User user = new User();
        user.setNickname("Test");
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);
        user = userRepository.save(user);
        int userId = user.getUserId();

        Post post = new Post();
        post.setUserId(userId);
        String testBody = "Test body";
        post.setBody(testBody);
        post.setCreationDate(LocalDateTime.now());
        post.setModificationDate(LocalDateTime.now());
        post = postRepository.save(post);
        int postId = post.getPostId();

        String token = jwtTokenUtil.getValidToken(userId, "Test");

        // when
        // then
        mockMvc.perform(get(String.format("/post/%d", postId)).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(postId))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.nickname").value("Test"))
                .andExpect(jsonPath("$.body").value(testBody));
    }

    @Test
    void deletePostThatExist() throws Exception {
        // given

        User user = new User();
        user.setNickname("Test");
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);
        user = userRepository.save(user);
        int userId = user.getUserId();

        Post post = new Post();
        post.setUserId(userId);
        String testBody = "Test body";
        post.setBody(testBody);
        post.setCreationDate(LocalDateTime.now());
        post.setModificationDate(LocalDateTime.now());
        post = postRepository.save(post);
        int postId = post.getPostId();

        String token = jwtTokenUtil.getValidToken(userId, "Test");

        // when
        // then
        mockMvc.perform(delete(String.format("/post/%d", postId)).header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
        Optional<Post> postOptional = postRepository.findById(postId);
        assertThat(postOptional.isPresent()).isFalse();
    }

    @Test
    void patchPostThatExist() throws Exception {
        // given
        User user = new User();
        user.setNickname("Test");
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);
        user = userRepository.save(user);
        int userId = user.getUserId();

        Post post = new Post();
        post.setUserId(userId);
        String testBody = "Test body";
        post.setBody(testBody);
        post.setCreationDate(LocalDateTime.now());
        post.setModificationDate(LocalDateTime.now());
        post = postRepository.save(post);
        int postId = post.getPostId();

        String token = jwtTokenUtil.getValidToken(userId, "Test");

        // when
        // then
        mockMvc.perform(patch(String.format("/post/%d", postId))
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                    "body": "New Test Body"
                                }"""))
                .andExpect(status().isOk());
        Post editedPost = postRepository.getById(postId);
        assertThat(editedPost.getBody()).isEqualTo("New Test Body");
    }

    @Test
    void createNewPost() throws Exception {
        // given
        User user = new User();
        user.setNickname("Test");
        user.setEmail("test@mail.com");
        user.setPassword("test");
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);
        user = userRepository.save(user);
        int userId = user.getUserId();

        String token = jwtTokenUtil.getValidToken(userId, "Test");

        // when
        // then
        mockMvc.perform(post("/post")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "body": "New Test Body",
                                    "picture": "Test picture"
                                }"""))
                .andExpect(status().isOk());

        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(1);
        Post post = posts.get(0);
        assertThat(post.getBody()).isEqualTo("New Test Body");
        assertThat(post.getPicture()).isEqualTo("Test picture");
    }


}
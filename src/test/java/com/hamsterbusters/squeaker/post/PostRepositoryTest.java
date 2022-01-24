package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.user.User;
import com.hamsterbusters.squeaker.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByCreationDateGreaterThan() {
        //given
        User user = new User();
        user.setNickname("Test");
        user.setEmail("test@mail.com");
        user.setPassword("test_password");
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);
        user = userRepository.save(user);
        int userId = user.getUserId();

        List<Post> posts = new ArrayList<>();
        posts.add(getPostOlderThanDays(3, userId));
        posts.add(getPostOlderThanDays(4, userId));
        posts.add(getPostOlderThanDays(5, userId));
        posts.add(getPostOlderThanDays(6, userId));
        postRepository.saveAll(posts);
        //when
        List<Post> result = postRepository.findByCreationDateGreaterThan(LocalDateTime.now().minusDays(5));
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    private Post getPostOlderThanDays(int days, int userId) {
        Post post = new Post();
        post.setUserId(userId);
        post.setBody("Test1");
        post.setCreationDate(LocalDateTime.now().minusDays(days));
        post.setModificationDate(LocalDateTime.now().minusDays(days));
        return post;
    }
}
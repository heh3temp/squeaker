package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.user.User;
import com.hamsterbusters.squeaker.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    private int postId = 1;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        postId = 1;
    }

    @Test
    void findByCreationDateGreaterThan() {
        //given
        User user = new User();
        user.setUserId(1);
        user.setNickname("test1");
        userRepository.save(user);

        List<Post> posts = new ArrayList<>();
        posts.add(getPostOlderThanDays(3));
        posts.add(getPostOlderThanDays(4));
        posts.add(getPostOlderThanDays(5));
        posts.add(getPostOlderThanDays(6));
        postRepository.saveAll(posts);
        //when
        List<Post> result = postRepository.findByCreationDateGreaterThan(LocalDateTime.now().minusDays(5));
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    private Post getPostOlderThanDays(int days) {
        Post post = new Post();
        post.setPostId(postId);
        postId++;
        post.setUserId(1);
        post.setBody("Test1");
        post.setCreationDate(LocalDateTime.now().minusDays(days));
        return post;
    }
}
package com.hamsterbusters.squeaker.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class PostController {

    @GetMapping("/popular_posts")
    public ResponseEntity<List<PostDto>> getPopularPosts() {
        List<PostDto> posts = List.of(
                new PostDto(
                        1,
                        "jan",
                        "Lubie placki",
                        null,
                        null,
                        0,
                        0
                ),
                new PostDto(
                        2,
                        "blazej",
                        "Nie lubie placki",
                        null,
                        null,
                        0,
                        0
                ),
                new PostDto(
                        3,
                        "jan",
                        "Kocham placki",
                        null,
                        null,
                        0,
                        0
                )
        );

        return ResponseEntity.ok()
                .body(posts);
    }

    @PostMapping("/post")
    public ResponseEntity<Void> addPost(@RequestBody PostDto post) {
        log.debug(post.toString());
        return ResponseEntity.ok()
                .build();
    }

}

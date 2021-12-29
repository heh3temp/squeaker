package com.hamsterbusters.squeaker.post;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @GetMapping("/popular_posts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
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
}

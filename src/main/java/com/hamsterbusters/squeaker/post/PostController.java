package com.hamsterbusters.squeaker.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping("/popular_posts")
    public ResponseEntity<List<PostDto>> getPopularPosts() {
        List<PostDto> posts = List.of(
                new PostDto(
                        1,
                        1,
                        "jan",
                        "Lubie placki",
                        null,
                        null,
                        0,
                        0,
                        true,
                        LocalDateTime.now()
                ),
                new PostDto(
                        2,
                        2,
                        "blazej",
                        "Nie lubie placki",
                        null,
                        null,
                        0,
                        0,
                        true,
                        LocalDateTime.now()
                ),
                new PostDto(
                        3,
                        3,
                        "jan",
                        "Kocham placki",
                        null,
                        null,
                        0,
                        0,
                        true,
                        LocalDateTime.now()
                )
        );

        return ResponseEntity.ok()
                .body(posts);
    }

    @PostMapping("/post")
    public ResponseEntity<Void> addPost(@RequestBody NewPostDto postDto) {
        postService.createNewPost(postDto);
        return ResponseEntity.ok()
                .build();
    }

}

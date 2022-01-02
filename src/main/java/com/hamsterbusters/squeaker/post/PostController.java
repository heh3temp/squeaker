package com.hamsterbusters.squeaker.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping("/popular_posts")
    public ResponseEntity<List<PostDto>> getPopularPosts(@RequestParam int hours) {

        List<PostDto> popularPosts = postService.getPopularPosts(hours);

        return ResponseEntity.ok()
                .body(popularPosts);
    }

    @PostMapping("/post")
    public ResponseEntity<Void> addPost(@RequestBody NewPostDto postDto) {
        postService.createNewPost(postDto);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getFollowedPosts() {

        List<PostDto> followedPosts = postService.getFollowedPosts();

        return  ResponseEntity.ok()
                .body(followedPosts);
    }
}

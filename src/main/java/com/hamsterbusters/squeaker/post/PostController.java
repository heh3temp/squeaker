package com.hamsterbusters.squeaker.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        List<Post> popularPosts = postService.getPopularPosts(hours);

        List<PostDto> posts =  popularPosts.stream().map(post -> postMapper.mapPostToDto(post, post.getUser())).collect(Collectors.toList());


        return ResponseEntity.ok()
                .body(posts);
    }

    @PostMapping("/post")
    public ResponseEntity<Void> addPost(@RequestBody CreatePostDto postDto) {
        postService.createNewPost(postDto);
        return ResponseEntity.ok()
                .build();
    }

}

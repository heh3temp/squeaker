package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.post.dto.NewPostDto;
import com.hamsterbusters.squeaker.post.dto.PostDto;
import com.hamsterbusters.squeaker.post.dto.EditPostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

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

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable int postId) {
        PostDto postDto = postService.getPost(postId);
        return ResponseEntity.ok()
                .body(postDto);
    }


    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return  ResponseEntity.ok()
                .build();
    }

    @PatchMapping("/post/{postId}")
    public ResponseEntity<Void> editPost(@PathVariable int postId, @RequestBody EditPostDto editPostDto) {
        postService.editPost(postId, editPostDto);
        return  ResponseEntity.ok()
                .build();
    }
}

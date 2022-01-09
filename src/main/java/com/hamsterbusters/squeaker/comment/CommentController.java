package com.hamsterbusters.squeaker.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<Void> addComment(@PathVariable int postId, @RequestBody NewCommentDto createPostDto) {
        commentService.addComment(postId, createPostDto);
        return ResponseEntity.ok().build();
    }
}

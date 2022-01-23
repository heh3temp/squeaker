package com.hamsterbusters.squeaker.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable int postId) {
        return ResponseEntity.ok()
                .body(
                        commentService.getComments(postId)
                );
    }

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<Void> addComment(@PathVariable int postId, @RequestBody NewCommentDto createPostDto) {
        commentService.addComment(postId, createPostDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int postId, @PathVariable int commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}

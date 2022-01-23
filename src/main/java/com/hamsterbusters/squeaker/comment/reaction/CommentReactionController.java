package com.hamsterbusters.squeaker.comment.reaction;


import com.hamsterbusters.squeaker.post.reaction.AddReactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentReactionController {
    private final CommentReactionService commentReactionService;

    @PostMapping("/post/{postId}/comment/{commentId}/reaction")
    public ResponseEntity<Void> addReaction(@PathVariable int postId, @PathVariable int commentId, @RequestBody AddCommentReactionRequest addReactionRequest) {
        commentReactionService.changeReaction(commentId, addReactionRequest.isLiked());
        return ResponseEntity.ok()
                .build();
    }
}

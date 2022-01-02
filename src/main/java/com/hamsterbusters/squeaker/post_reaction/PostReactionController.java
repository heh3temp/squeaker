package com.hamsterbusters.squeaker.post_reaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostReactionController {

    private final PostReactionService postService;

    @PostMapping("/post/{postId}/reaction")
    public ResponseEntity<Void> addReaction(@PathVariable int postId, @RequestBody AddReactionRequest addReactionRequest) {
        postService.changeReaction(postId, addReactionRequest.isLiked());
        return ResponseEntity.ok()
                .build();
    }

}

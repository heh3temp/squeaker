package com.hamsterbusters.squeaker.post_reaction;

import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReactionService {

    private final PostReactionRepository postReactionRepository;

    public void changeReaction(int postId, boolean isLiked) {
        int userId = JwtTokenVerifier.getPrincipalFromJwtToken();
        if (isLiked) {
            addReaction(userId, postId);
        } else {
            removeReaction(userId, postId);
        }
    }
    private void addReaction(int userId, int postId) {
        PostReactionCompositeKey postReactionCompositeKey = new PostReactionCompositeKey(userId, postId);
        PostReaction postReaction = new PostReaction(postReactionCompositeKey);
        postReactionRepository.save(postReaction);
    }

    private void removeReaction(int userId, int postId) {
        PostReactionCompositeKey postReactionCompositeKey = new PostReactionCompositeKey(userId, postId);
        postReactionRepository.deleteById(postReactionCompositeKey);
    }

}

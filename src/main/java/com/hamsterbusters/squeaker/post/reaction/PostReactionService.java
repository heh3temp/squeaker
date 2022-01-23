package com.hamsterbusters.squeaker.post.reaction;

import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
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
        try {
            postReactionRepository.deleteById(postReactionCompositeKey);
        } catch (DataAccessException dataAccessException) {
            log.warn("Can't delete reaction that does not exist! user_id: {}, post_id: {}", userId, postId);
        }
    }

}

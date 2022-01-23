package com.hamsterbusters.squeaker.comment.reaction;

import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import com.hamsterbusters.squeaker.post.reaction.PostReaction;
import com.hamsterbusters.squeaker.post.reaction.PostReactionCompositeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentReactionService {

    private final CommentReactionRepository commentReactionRepository;

    public void changeReaction(int commentId, boolean isLiked) {
        int userId = JwtTokenVerifier.getPrincipalFromJwtToken();
        if (isLiked) {
            addReaction(userId, commentId);
        } else {
            removeReaction(userId, commentId);
        }
    }

    private void addReaction(int userId, int commentId) {
        CommentReactionCompositeKey commentReactionCompositeKey = new CommentReactionCompositeKey(userId, commentId);
        CommentReaction commentReaction = new CommentReaction(commentReactionCompositeKey);
        commentReactionRepository.save(commentReaction);
    }

    private void removeReaction(int userId, int commentId) {
        CommentReactionCompositeKey commentReactionCompositeKey = new CommentReactionCompositeKey(userId, commentId);
        try {
            commentReactionRepository.deleteById(commentReactionCompositeKey);
        } catch (DataAccessException dataAccessException) {
            log.warn("Can't delete reaction that does not exist! user_id: {}, comment_id: {}", userId, commentId);
        }
    }
}

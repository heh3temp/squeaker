package com.hamsterbusters.squeaker.comment.reaction;

import org.springframework.data.repository.CrudRepository;

public interface CommentReactionRepository extends CrudRepository<CommentReaction, CommentReactionCompositeKey> {
}

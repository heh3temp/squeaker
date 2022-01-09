package com.hamsterbusters.squeaker.post.reaction;

import org.springframework.data.repository.CrudRepository;

public interface PostReactionRepository extends CrudRepository<PostReaction, PostReactionCompositeKey> {
}

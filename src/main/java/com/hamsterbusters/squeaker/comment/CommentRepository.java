package com.hamsterbusters.squeaker.comment;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
}

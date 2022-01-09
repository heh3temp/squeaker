package com.hamsterbusters.squeaker.comment;

import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public void addComment(int postId, NewCommentDto postDto) {
        Comment comment = commentMapper.mapDtoToComment(postDto);

        comment.setPostId(postId);
        comment.setUserId(JwtTokenVerifier.getPrincipalFromJwtToken());
        commentRepository.save(comment);
    }

}

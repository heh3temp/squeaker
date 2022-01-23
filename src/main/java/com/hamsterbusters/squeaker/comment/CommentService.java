package com.hamsterbusters.squeaker.comment;

import com.hamsterbusters.squeaker.comment.dto.CommentDto;
import com.hamsterbusters.squeaker.comment.dto.NewCommentDto;
import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<CommentDto> getComments(int postId) {
        return commentRepository.findCommentsByPostId(postId)
                .stream()
                .map(commentMapper::mapCommentToDto)
                .toList();
    }
}

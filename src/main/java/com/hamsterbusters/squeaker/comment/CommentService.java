package com.hamsterbusters.squeaker.comment;

import com.hamsterbusters.squeaker.comment.dto.CommentDto;
import com.hamsterbusters.squeaker.comment.dto.EditCommentDto;
import com.hamsterbusters.squeaker.comment.dto.NewCommentDto;
import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import com.hamsterbusters.squeaker.post.Post;
import com.hamsterbusters.squeaker.post.exception.IllegalOperationException;
import com.hamsterbusters.squeaker.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }

    public void deleteComment(int commentId) {
        int userId = JwtTokenVerifier.getPrincipalFromJwtToken();
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Comment comment = commentOptional.orElseThrow(() -> new EntityNotFoundException("Comment not found in the database"));
        int authorId =comment.getUserId();

        if (userId != authorId) {
            throw new IllegalOperationException("Comments can only be deleted by the author");
        }

        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void editComment(int commentId, EditCommentDto editCommentDto) {
        int userId = JwtTokenVerifier.getPrincipalFromJwtToken();
        Comment comment;
        try {
            comment = commentRepository.getById(commentId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Comment does not exist");
        }

        if (userId != comment.getUserId()) {
            throw new IllegalOperationException("Comments can only be edited by the author");
        }


        String body = editCommentDto.getBody();
        if (body != null) {
            comment.setBody(body);
        }

        String picture = editCommentDto.getPicture();
        if (picture != null) {
            comment.setPicture(picture);
        }
    }
}

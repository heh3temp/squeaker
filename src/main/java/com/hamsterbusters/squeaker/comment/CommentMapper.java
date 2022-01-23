package com.hamsterbusters.squeaker.comment;

import com.hamsterbusters.squeaker.comment.dto.CommentDto;
import com.hamsterbusters.squeaker.comment.dto.NewCommentDto;
import com.hamsterbusters.squeaker.comment.reaction.CommentReaction;
import com.hamsterbusters.squeaker.comment.reaction.CommentReactionCompositeKey;
import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import com.hamsterbusters.squeaker.post.Post;
import com.hamsterbusters.squeaker.post.reaction.PostReaction;
import com.hamsterbusters.squeaker.post.reaction.PostReactionCompositeKey;
import com.hamsterbusters.squeaker.user.User;
import com.hamsterbusters.squeaker.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class CommentMapper {

    private final ModelMapper modelMapper;

    public Comment mapDtoToComment(NewCommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setCreationDate(LocalDateTime.now());
        comment.setModificationDate(LocalDateTime.now());
        return comment;
    }

    public CommentDto mapCommentToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

        User user = comment.getUser();
        commentDto.setNickname(user.getNickname());
        commentDto.setAvatar(user.getAvatar());
        commentDto.setLikesCount(comment.getCommentReactions().size());
        boolean isLiked = isCommentLikedByUser(comment);
        commentDto.setLiked(isLiked);

        return commentDto;
    }

    private boolean isCommentLikedByUser(Comment comment) {
        CommentReactionCompositeKey commentReactionCompositeKey = new CommentReactionCompositeKey(
                JwtTokenVerifier.getPrincipalFromJwtToken(),
                comment.getCommentId()
        );
        CommentReaction commentReaction = new CommentReaction(commentReactionCompositeKey);
        return comment.getCommentReactions().contains(commentReaction);
    }
}

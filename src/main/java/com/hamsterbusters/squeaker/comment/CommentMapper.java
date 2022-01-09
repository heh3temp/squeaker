package com.hamsterbusters.squeaker.comment;

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
        commentDto.setLikesCount(UserService.generate(0, 20));
        commentDto.setLiked(Math.random() < 0.5);

        return commentDto;
    }
}

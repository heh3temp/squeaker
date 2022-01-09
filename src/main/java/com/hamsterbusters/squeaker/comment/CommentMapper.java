package com.hamsterbusters.squeaker.comment;

import com.hamsterbusters.squeaker.post.Post;
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
}

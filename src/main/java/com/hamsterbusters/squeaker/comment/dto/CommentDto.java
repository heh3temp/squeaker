package com.hamsterbusters.squeaker.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private int commentId;
    private int userId;
    private String nickname;
    private String avatar;
    private String body;
    private String picture;
    private int likesCount;
    private boolean liked;
    private LocalDateTime creationDate;

}

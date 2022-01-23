package com.hamsterbusters.squeaker.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private int postId;
    private int userId;
    private String nickname;
    private String body;
    private Object picture;
    private Object avatar;
    private int commentsCount;
    private int likesCount;
    private boolean liked;
    private LocalDateTime creationDate;

}

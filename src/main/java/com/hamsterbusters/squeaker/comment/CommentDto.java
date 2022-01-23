package com.hamsterbusters.squeaker.comment;

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

    private int userId;
    private String nickname;
    private String avatar;
    private String body;
    private int likesCount;
    private boolean liked;
    private LocalDateTime creationDate;

}

package com.hamsterbusters.squeaker.post;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private int postId;
    private int userId;
    private String nickname;
    private String text;
    private Object picture;
    private Object avatar;
    private int commentsCount;
    private int likesCount;
    private boolean liked;

}

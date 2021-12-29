package com.hamsterbusters.squeaker.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostDto {

    private int userId;
    private String username;
    private String text;
    private Object image;
    private Object avatar;
    private int commmsCount;
    private int likesCount;

}

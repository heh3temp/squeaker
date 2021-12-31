package com.hamsterbusters.squeaker.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDto {

    private String nickname;
    private String description;
    private Object background;
    private Object avatar;
    private int followingCount;
    private int followersCount;

}

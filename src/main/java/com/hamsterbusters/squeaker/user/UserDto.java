package com.hamsterbusters.squeaker.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private String username;
    private String description;
    private Object background;
    private Object avatar;
    private int followingCount;
    private int followersCount;

}

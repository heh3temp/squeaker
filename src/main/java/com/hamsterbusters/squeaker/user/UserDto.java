package com.hamsterbusters.squeaker.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String nickname;
    private String description;
    private String background;
    private String avatar;
    private int followingCount;
    private int followersCount;
    private boolean followStatus;

}

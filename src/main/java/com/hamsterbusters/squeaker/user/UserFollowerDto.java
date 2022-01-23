package com.hamsterbusters.squeaker.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserFollowerDto {

    private int userId;
    private String nickname;
    private String description;
    private String avatar;
    private boolean followStatus;

}

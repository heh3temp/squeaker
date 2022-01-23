package com.hamsterbusters.squeaker.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {

    private String nickname;
    private String description;
    private String email;
    private String password;
    private String avatar;
    private String bgImage;
}

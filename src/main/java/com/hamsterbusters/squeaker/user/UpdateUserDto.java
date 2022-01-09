package com.hamsterbusters.squeaker.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {

    private String nickname;
    private String description;
    private String email;
    private String password;

}

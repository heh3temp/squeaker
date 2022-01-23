package com.hamsterbusters.squeaker.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserDto {

    private String nickname;
    private String password;
    private String email;
}


package com.hamsterbusters.squeaker.user.squeaker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("application.squeaker")
public class SqueakerProperties {

    private String nickname;
    private String email;
    private String password;
    private String description;

}

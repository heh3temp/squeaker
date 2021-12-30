package com.hamsterbusters.squeaker.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "USERS")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String nickname;
    private String password;
    private String email;
    private String password;
    private LocalDateTime joinDate;
    private LocalDateTime lastActivity;
    private boolean isActive;
    private byte[] avatar;

}

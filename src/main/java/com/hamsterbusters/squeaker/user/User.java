package com.hamsterbusters.squeaker.user;

import com.hamsterbusters.squeaker.follower.Follower;
import com.hamsterbusters.squeaker.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String nickname;
    private String email;
    private String password;
    private LocalDateTime joinDate;
    private LocalDateTime lastActivity;
    private boolean isActive;
    private String description;
    private byte[] avatar;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "followed")
    private List<Follower> followed;

    @OneToMany(mappedBy = "follower")
    private List<Follower> followers;

}

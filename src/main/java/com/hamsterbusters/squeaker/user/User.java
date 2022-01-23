package com.hamsterbusters.squeaker.user;

import com.hamsterbusters.squeaker.comment.Comment;
import com.hamsterbusters.squeaker.follower.Follower;
import com.hamsterbusters.squeaker.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


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
    private String avatar;
    private  String bgImage;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "follower")
    private Set<Follower> followed;

    @OneToMany(mappedBy = "followed")
    private Set<Follower> followers;

}

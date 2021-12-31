package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private Integer userId;
    private String body;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private byte[] picture;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private User user;

}

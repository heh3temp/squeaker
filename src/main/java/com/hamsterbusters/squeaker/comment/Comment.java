package com.hamsterbusters.squeaker.comment;

import com.hamsterbusters.squeaker.post.Post;
import com.hamsterbusters.squeaker.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "COMMENTS")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private Integer userId;
    private Integer postId;
    private String body;
    private String picture;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false, insertable = false, updatable = false)
    private Post post;

}

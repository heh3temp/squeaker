package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.post_reaction.PostReaction;
import com.hamsterbusters.squeaker.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    @OneToMany(mappedBy = "post")
    private Set<PostReaction> postReactions;

}

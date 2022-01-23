package com.hamsterbusters.squeaker.comment;

import com.hamsterbusters.squeaker.comment.reaction.CommentReaction;
import com.hamsterbusters.squeaker.post.Post;
import com.hamsterbusters.squeaker.post.reaction.PostReaction;
import com.hamsterbusters.squeaker.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    @OneToMany(mappedBy = "comment", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Set<CommentReaction> commentReactions;

}

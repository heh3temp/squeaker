package com.hamsterbusters.squeaker.follower;

import com.hamsterbusters.squeaker.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "FOLLOWERS")
public class Follower {

    @EmbeddedId
    private FollowerCompositeKey followerCompositeKey;

    @ManyToOne
    @JoinColumn(name = "followedId", nullable = false, insertable = false, updatable = false)
    private User followed;

    @ManyToOne
    @JoinColumn(name = "followerId", nullable = false, insertable = false, updatable = false)
    private User follower;

}

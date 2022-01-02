package com.hamsterbusters.squeaker.follower;

import com.hamsterbusters.squeaker.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
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

    public Follower(FollowerCompositeKey followerCompositeKey) {
        this.followerCompositeKey = followerCompositeKey;
    }
}

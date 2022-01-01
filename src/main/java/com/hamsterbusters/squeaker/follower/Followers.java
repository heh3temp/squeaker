package com.hamsterbusters.squeaker.follower;

import com.hamsterbusters.squeaker.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "FOLLOWERS")
public class Followers {
    @Id
    private Integer followedId;
    private Integer followerId;

    @ManyToOne
    @JoinColumn(name = "followedId", nullable = false, insertable = false, updatable = false)
    private User user;
}

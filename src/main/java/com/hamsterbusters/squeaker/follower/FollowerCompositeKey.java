package com.hamsterbusters.squeaker.follower;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class FollowerCompositeKey implements Serializable {

    private Integer followedId;
    private Integer followerId;

}

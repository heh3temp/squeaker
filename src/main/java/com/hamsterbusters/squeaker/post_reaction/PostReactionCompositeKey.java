package com.hamsterbusters.squeaker.post_reaction;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PostReactionCompositeKey implements Serializable {

    private Integer userId;
    private Integer postId;

}

package com.hamsterbusters.squeaker.comment.reaction;


import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CommentReactionCompositeKey implements Serializable {
    private Integer reactionAuthorId;
    private Integer commentId;
}

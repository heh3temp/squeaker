package com.hamsterbusters.squeaker.post_reaction;

import com.hamsterbusters.squeaker.post.Post;
import com.hamsterbusters.squeaker.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "POST_REACTIONS")
public class PostReaction {

    public PostReaction(PostReactionCompositeKey postReactionCompositeKey) {
        this.postReactionCompositeKey = postReactionCompositeKey;
    }

    @EmbeddedId
    private PostReactionCompositeKey postReactionCompositeKey;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false, insertable = false, updatable = false)
    private Post post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostReaction)) return false;

        PostReaction that = (PostReaction) o;

        return getPostReactionCompositeKey().equals(that.getPostReactionCompositeKey());
    }

    @Override
    public int hashCode() {
        return getPostReactionCompositeKey().hashCode();
    }
}

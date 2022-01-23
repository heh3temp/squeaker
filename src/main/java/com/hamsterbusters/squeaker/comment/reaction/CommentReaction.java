package com.hamsterbusters.squeaker.comment.reaction;


import com.hamsterbusters.squeaker.comment.Comment;
import com.hamsterbusters.squeaker.post.Post;
import com.hamsterbusters.squeaker.post.reaction.PostReaction;
import com.hamsterbusters.squeaker.post.reaction.PostReactionCompositeKey;
import com.hamsterbusters.squeaker.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "COMMENT_REACTIONS")
public class CommentReaction {

    @EmbeddedId
    private CommentReactionCompositeKey commentReactionCompositeKey;

    @ManyToOne
    @JoinColumn(name = "reactionAuthorId", nullable = false, insertable = false, updatable = false)
    private User reactionAuthor;

    @ManyToOne
    @JoinColumn(name = "commentId", nullable = false, insertable = false, updatable = false)
    private Comment comment;

    public CommentReaction(CommentReactionCompositeKey commentReactionCompositeKey) {
        this.commentReactionCompositeKey = commentReactionCompositeKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentReaction)) return false;

        CommentReaction that = (CommentReaction) o;

        return getCommentReactionCompositeKey().equals(that.getCommentReactionCompositeKey());
    }

    @Override
    public int hashCode() {
        return getCommentReactionCompositeKey().hashCode();
    }
}

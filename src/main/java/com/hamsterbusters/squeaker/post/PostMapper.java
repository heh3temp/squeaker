package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.post_reaction.PostReaction;
import com.hamsterbusters.squeaker.post_reaction.PostReactionCompositeKey;
import com.hamsterbusters.squeaker.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.hamsterbusters.squeaker.user.UserService.generate;


@RequiredArgsConstructor
@Component
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostDto mapPostToDto(Post post, User user) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setUserId(user.getUserId());
        postDto.setNickname(user.getNickname());
        postDto.setAvatar(user.getAvatar());
        boolean isLiked = isPostLikedByUser(post, user);
        postDto.setLiked(isLiked);
        postDto.setCommentsCount(0);
        postDto.setLikesCount(post.getPostReactions().size());
        return postDto;
    }

    private boolean isPostLikedByUser(Post post, User user) {
        PostReactionCompositeKey postReactionCompositeKey = new PostReactionCompositeKey(user.getUserId(), post.getPostId());
        PostReaction postReaction = new PostReaction(postReactionCompositeKey);
        return post.getPostReactions().contains(postReaction);
    }

    public Post mapDtoToPost(NewPostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setCreationDate(LocalDateTime.now());
        post.setModificationDate(LocalDateTime.now());
        return post;
    }
}

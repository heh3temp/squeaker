package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import com.hamsterbusters.squeaker.post.reaction.PostReaction;
import com.hamsterbusters.squeaker.post.reaction.PostReactionCompositeKey;
import com.hamsterbusters.squeaker.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Component
public class PostMapper {

    private final ModelMapper modelMapper;

    public PostDto mapPostToDto(Post post, User user) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setUserId(user.getUserId());
        postDto.setNickname(user.getNickname());
        postDto.setAvatar(user.getAvatar());
        boolean isLiked = isPostLikedByUser(post);
        postDto.setLiked(isLiked);
        postDto.setCommentsCount(post.getComments().size());
        postDto.setLikesCount(post.getPostReactions().size());
        return postDto;
    }

    private boolean isPostLikedByUser(Post post) {
        PostReactionCompositeKey postReactionCompositeKey = new PostReactionCompositeKey(
                JwtTokenVerifier.getPrincipalFromJwtToken(),
                post.getPostId()
        );
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

package com.hamsterbusters.squeaker.post;

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
        postDto.setCommentsCount(0);
        postDto.setLikesCount(generate(0, 20));
        postDto.setLiked(Math.random() > 0.5);
        return postDto;
    }

    public Post mapDtoToPost(CreatePostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setCreationDate(LocalDateTime.now());
        post.setModificationDate(LocalDateTime.now());
        return post;
    }
}

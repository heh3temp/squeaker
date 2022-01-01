package com.hamsterbusters.squeaker.post;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public void createNewPost(NewPostDto postDto) {
        Post post = mapDtoToPost(postDto);
        post.setUserId(getPrincipalFromJwtToken());
        postRepository.save(post);
    }

    private Post mapDtoToPost(NewPostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setCreationDate(LocalDateTime.now());
        post.setModificationDate(LocalDateTime.now());
        return post;
    }

    private int getPrincipalFromJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Integer.parseInt((String) authentication.getPrincipal());

    }
}

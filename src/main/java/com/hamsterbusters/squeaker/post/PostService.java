package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public void createNewPost(NewPostDto postDto) {
        Post post = postMapper.mapDtoToPost(postDto);
        post.setUserId(JwtTokenVerifier.getPrincipalFromJwtToken());
        postRepository.save(post);
    }

    public List<PostDto> getPopularPosts(int hours) {
        List<Post> popularPosts = postRepository.findByCreationDateGreaterThan(LocalDateTime.now().minusHours(hours));

        return popularPosts.stream()
                .map(post -> postMapper.mapPostToDto(post, post.getUser()))
                .collect(Collectors.toList());
    }

}

package com.hamsterbusters.squeaker.post;

import com.hamsterbusters.squeaker.comment.Comment;
import com.hamsterbusters.squeaker.follower.Follower;
import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import com.hamsterbusters.squeaker.user.User;
import com.hamsterbusters.squeaker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Comparator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public void createNewPost(NewPostDto postDto) {
        Post post = postMapper.mapDtoToPost(postDto);
        post.setUserId(JwtTokenVerifier.getPrincipalFromJwtToken());
        postRepository.save(post);
    }

    public List<PostDto> getPopularPosts(int hours) {
        List<Post> popularPosts = postRepository.findByCreationDateGreaterThan(LocalDateTime.now().minusHours(hours));

        return popularPosts.stream()
                .map(post -> postMapper.mapPostToDto(post, post.getUser()))
                .sorted(Comparator.comparing(PostDto::getLikesCount).reversed())
                .collect(Collectors.toList());
    }

    public List<PostDto> getFollowedPosts() {

        User user = userRepository.getById(JwtTokenVerifier.getPrincipalFromJwtToken());
        List<PostDto> followedPosts = new ArrayList<>();
        for (Follower follower: user.getFollowed()) {
            User followedUser = follower.getFollowed();
            followedPosts.addAll(
                    followedUser.getPosts().stream()
                            .map(post -> postMapper.mapPostToDto(post, followedUser))
                            .collect(Collectors.toList())
            );
        }

        followedPosts.addAll(
                user.getPosts().stream()
                        .map(post -> postMapper.mapPostToDto(post, user))
                        .collect(Collectors.toList())
        );

        return followedPosts.stream()
                .sorted(Comparator.comparing(PostDto::getCreationDate).reversed())
                .collect(Collectors.toList());
    }

    public void deletePost(int postId) {
        int userId = JwtTokenVerifier.getPrincipalFromJwtToken();
        Optional<Post> postOptional= postRepository.findById(postId);
        Post post = postOptional.orElseThrow(() -> new EntityNotFoundException("Post not found in the database"));

        int authorId = post.getUserId();

        if (userId != authorId) {
            throw new IllegalOperationException("Posts can only be deleted by the author");
        }
        postRepository.deleteById(postId);
    }
}

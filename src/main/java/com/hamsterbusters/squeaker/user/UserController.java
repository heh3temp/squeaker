package com.hamsterbusters.squeaker.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.hamsterbusters.squeaker.post.PostDto;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        return ResponseEntity.ok()
                .body(
                        userRepository.findById(userId).get()
                );
    }

    @PostMapping("/user")
    public ResponseEntity<Void> addUser(@RequestBody UserDto user) {
        log.debug(user.toString());
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable int userId) {
        List<PostDto> posts = List.of(
                new PostDto(
                        1,
                        "patryk",
                        "Lubie placki",
                        null,
                        null,
                        0,
                        0
                ),
                new PostDto(
                        2,
                        "michał",
                        "Nie lubie placki",
                        null,
                        null,
                        0,
                        0
                ),
                new PostDto(
                        3,
                        "jan",
                        "Kocham placki",
                        null,
                        null,
                        0,
                        0
                )
        );

        return ResponseEntity.ok()
                .body(posts);
    }
}

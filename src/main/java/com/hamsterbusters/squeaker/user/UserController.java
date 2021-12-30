package com.hamsterbusters.squeaker.user;

import com.hamsterbusters.squeaker.post.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        return ResponseEntity.ok()
                .body(
                        userRepository.findById(userId).get()
                );
    }

    @PostMapping("/user")
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        log.debug(user.toString());
        userService.register(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable int userId) {
        List<PostDto> posts = List.of(
                new PostDto(
                        1,
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

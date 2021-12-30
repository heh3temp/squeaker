package com.hamsterbusters.squeaker.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.hamsterbusters.squeaker.post.PostDto;

@RestController
public class UserController {

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public ResponseEntity<UserDto> getUser(@PathVariable int userId) {
        return ResponseEntity.ok()
                .body(
                        new UserDto(
                                "jan",
                                "description",
                                null,
                                null,
                                0,
                                0
                        )
                );
    }

    @GetMapping("/user/{userId}/posts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
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

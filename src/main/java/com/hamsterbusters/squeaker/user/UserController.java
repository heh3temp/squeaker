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

    private final UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable int userId) {
        return ResponseEntity.ok()
                .body(
                        userService.getUserDtoById(userId)
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
        List<PostDto> userPosts = userService.getUserPosts(userId);
        return ResponseEntity.ok()
                .body(userPosts);
    }

}

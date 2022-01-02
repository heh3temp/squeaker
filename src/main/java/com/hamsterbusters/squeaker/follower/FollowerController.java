package com.hamsterbusters.squeaker.follower;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FollowerController {

    private final FollowerService followerService;

    @PostMapping("/user/{userId}/follow")
    public ResponseEntity<Void> followUser(@PathVariable int userId, @RequestBody FollowerRequest followerRequest) {
        followerService.followUser(userId, followerRequest);
        return ResponseEntity.ok().build();
    }


}

package com.hamsterbusters.squeaker.follower;

import com.hamsterbusters.squeaker.user.UserFollowerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FollowerController {

    private final FollowerService followerService;

    @PostMapping("/user/{userId}/follow")
    public ResponseEntity<Void> followUser(@PathVariable int userId, @RequestBody FollowerRequest followerRequest) {
        followerService.followUser(userId, followerRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/followed")
    public ResponseEntity<List<UserFollowerDto>> getUserFollowed(@PathVariable int userId) {
        List<UserFollowerDto> userFollowerDtos = followerService.getUserFollowed(userId);
        return ResponseEntity.ok()
                .body(userFollowerDtos);
    }

    @GetMapping("/user/{userId}/followers")
    public ResponseEntity<List<UserFollowerDto>> getUserFollowers(@PathVariable int userId) {
        List<UserFollowerDto> userFollowerDtos = followerService.getUserFollowers(userId);
        return ResponseEntity.ok()
                .body(userFollowerDtos);
    }

}

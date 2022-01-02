package com.hamsterbusters.squeaker.follower;

import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowerService {

    private final FollowerRepository followerRepository;

    public void followUser(int userId, FollowerRequest followerRequest) {
        if (followerRequest.isFollow()) {
            addFollower(userId);
        } else {
            removeFollower(userId);
        }
    }

    private void addFollower(int userId) {
        FollowerCompositeKey followerCompositeKey = new FollowerCompositeKey(
                userId,
                JwtTokenVerifier.getPrincipalFromJwtToken()
        );
        Follower follower = new Follower(followerCompositeKey);
        followerRepository.save(follower);
    }

    private void removeFollower(int userId) {
        int followerId = JwtTokenVerifier.getPrincipalFromJwtToken();
        FollowerCompositeKey followerCompositeKey = new FollowerCompositeKey(
                userId,
                followerId
        );
        try {
            followerRepository.deleteById(followerCompositeKey);
        } catch (DataAccessException dataAccessException) {
            log.warn("Can't delete follower that does not exist! followedId {}, followerId {}", userId, followerId);
        }
    }

}

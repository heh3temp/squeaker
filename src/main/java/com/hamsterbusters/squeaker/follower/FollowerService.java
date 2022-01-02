package com.hamsterbusters.squeaker.follower;

import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import com.hamsterbusters.squeaker.user.User;
import com.hamsterbusters.squeaker.user.UserFollowerDto;
import com.hamsterbusters.squeaker.user.UserMapper;
import com.hamsterbusters.squeaker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

    public List<UserFollowerDto> getUserFollowed(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        return user.getFollowed().stream()
                .map(Follower::getFollowed)
                .map(userMapper::mapUserToUserFollowerDto)
                .toList();
    }

    public List<UserFollowerDto> getUserFollowers(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        return user.getFollowers().stream()
                .map(Follower::getFollower)
                .map(userMapper::mapUserToUserFollowerDto)
                .toList();
    }

}

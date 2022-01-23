package com.hamsterbusters.squeaker.user;

import com.hamsterbusters.squeaker.follower.Follower;
import com.hamsterbusters.squeaker.follower.FollowerCompositeKey;
import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import com.hamsterbusters.squeaker.user.dto.UserDto;
import com.hamsterbusters.squeaker.user.dto.UserFollowerDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDto mapUserToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setDescription(user.getDescription());

        userDto.setFollowingCount(user.getFollowed().size());
        userDto.setFollowersCount(user.getFollowers().size());
        boolean isFollowed = isFollowedByUser(user);
        userDto.setFollowStatus(isFollowed);
        return userDto;
    }

    public User mapCreateUserDtoToUser(CreateUserDto createUserDto) {
        User user = modelMapper.map(createUserDto, User.class);
        return user;
    }

    private boolean isFollowedByUser(User user) {
        FollowerCompositeKey followerCompositeKey = new FollowerCompositeKey(
                user.getUserId(),
                JwtTokenVerifier.getPrincipalFromJwtToken()
        );
        Follower follower = new Follower(followerCompositeKey);
        return user.getFollowers().contains(follower);
    }

    public UserFollowerDto mapUserToUserFollowerDto(User user) {
        UserFollowerDto userDto = modelMapper.map(user, UserFollowerDto.class);

        boolean isFollowed = isFollowedByUser(user);
        userDto.setFollowStatus(isFollowed);
        return userDto;
    }

}

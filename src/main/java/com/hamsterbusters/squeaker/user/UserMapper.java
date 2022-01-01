package com.hamsterbusters.squeaker.user;

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

        return userDto;
    }
}

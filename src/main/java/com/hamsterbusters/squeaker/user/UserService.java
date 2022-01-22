package com.hamsterbusters.squeaker.user;

import com.hamsterbusters.squeaker.follower.FollowerService;
import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import com.hamsterbusters.squeaker.post.Post;
import com.hamsterbusters.squeaker.post.dto.PostDto;
import com.hamsterbusters.squeaker.post.PostMapper;
import com.hamsterbusters.squeaker.user.dto.UpdateUserDto;
import com.hamsterbusters.squeaker.user.dto.UserDto;
import com.hamsterbusters.squeaker.user.squeaker.SqueakerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final FollowerService followerService;
    private final SqueakerProperties squeakerProperties;

    public void register(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);

        userRepository.save(user);

        User squeaker = getUserByNickname(squeakerProperties.getNickname());
        followerService.addSqueakerToFollowed(user.getUserId(), squeaker.getUserId());
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByNickname(nickname);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getNickname())
                .password(user.getPassword())
                .authorities(authorities)
                .disabled(!user.isActive())
                .build();
    }

    public UserDto getUserDtoById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        return userMapper.mapUserToDto(user);
    }

    public User getUserByNickname(String nickname) {
        Optional<User> userOptional = userRepository.findUserByNickname(nickname);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
    }

    public List<PostDto> getUserPosts(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        List<Post> posts = user.getPosts();
        return posts.stream()
                .map(post -> postMapper.mapPostToDto(post, user))
                .sorted(Comparator.comparing(PostDto::getCreationDate).reversed())
                .collect(Collectors.toList());
    }

    public static int generate(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    @Transactional
    public void updateUser(UpdateUserDto userDto) {
        int userId = JwtTokenVerifier.getPrincipalFromJwtToken();
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));

        String nickname = userDto.getNickname();
        if (nickname != null)
            user.setNickname(nickname);

        String description = userDto.getDescription();
        if (description != null)
            user.setDescription(description);

        String email = userDto.getEmail();
        if (email != null)
            user.setEmail(email);

        String password = userDto.getPassword();
        if (password != null)
            user.setPassword(passwordEncoder.encode(password));
    }

    @Transactional
    public void deleteUser() {
        int userId = JwtTokenVerifier.getPrincipalFromJwtToken();
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        user.setActive(false);
        user.setEmail("****");
    }
}

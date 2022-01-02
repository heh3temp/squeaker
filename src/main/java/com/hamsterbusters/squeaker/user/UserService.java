package com.hamsterbusters.squeaker.user;

import com.hamsterbusters.squeaker.post.Post;
import com.hamsterbusters.squeaker.post.PostDto;
import com.hamsterbusters.squeaker.post.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PostMapper postMapper;

    public void register(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByNickname(nickname);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(
                user.getNickname(),
                user.getPassword(),
                authorities
        );
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
                .collect(Collectors.toList());
    }

    public static int generate(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

}

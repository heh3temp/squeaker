package com.hamsterbusters.squeaker.user;

import com.hamsterbusters.squeaker.follower.FollowerService;
import com.hamsterbusters.squeaker.jwt.JwtTokenVerifier;
import com.hamsterbusters.squeaker.post.Post;
import com.hamsterbusters.squeaker.post.PostDto;
import com.hamsterbusters.squeaker.post.PostMapper;
import com.hamsterbusters.squeaker.user.squeaker.SqueakerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.validator.routines.EmailValidator;


import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
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

    public void register(CreateUserDto createUserDto) {

        if (!this.isValidEmail(createUserDto.getEmail())) {
            throw new InvalidValueException("Invalid email");
        }

        if (!this.isValidPassword(createUserDto.getPassword())) {
            throw new InvalidValueException("Invalid password. Must be between 8 and 100 characters and include small and large letters, digits and special characters.");
        }

        if (!this.isValidNickname(createUserDto.getNickname())) {
            throw new InvalidValueException("Invalid nickname. Valid can include letters, digits, underscores and must be between 3 and 40 characters long");
        }

        String encodedPassword = passwordEncoder.encode(createUserDto.getPassword());

        User user = userMapper.mapCreateUserDtoToUser(createUserDto);

        user.setPassword(encodedPassword);
        user.setJoinDate(LocalDateTime.now());
        user.setLastActivity(LocalDateTime.now());
        user.setActive(true);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidValueException("Nickname and email must be unique");
        }


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
        if (nickname != null) {
            if (!this.isValidNickname(nickname)) {
                throw new InvalidValueException("Invalid nickname. Valid can include letters, digits, underscores and must be between 3 and 40 characters long");
            }
            user.setNickname(nickname);
        }
            

        String description = userDto.getDescription();
        if (description != null)
            user.setDescription(description);

        String email = userDto.getEmail();
        if (email != null) {
            if (!this.isValidEmail(email)) {
                throw new InvalidValueException("Invalid email");
            }
            user.setEmail(email);
        }

        String password = userDto.getPassword();
        if (password != null) {
            if (!this.isValidPassword(password)) {
                throw new InvalidValueException("Invalid password. Must be between 8 and 100 characters and include small and large letters, digits and special characters.");
            }
            user.setPassword(passwordEncoder.encode(password));
        }

        String avatar = userDto.getAvatar();
        if (avatar != null)
            user.setAvatar(avatar);
    }

    @Transactional
    public void deleteUser() {
        int userId = JwtTokenVerifier.getPrincipalFromJwtToken();
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
        user.setActive(false);
        user.setEmail("****");
    }

    private boolean isValidPassword(String password) {
        String validPasswordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,100}$";

        Pattern pattern = Pattern.compile(validPasswordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {

        return EmailValidator.getInstance().isValid(email);
    }

    private boolean isValidNickname(String nickname) {
        String validNicknameRegex = "^[a-zA-Z0-9_]{3,30}$";

        Pattern pattern = Pattern.compile(validNicknameRegex);
        Matcher matcher = pattern.matcher(nickname);
        return matcher.matches();
    }
}

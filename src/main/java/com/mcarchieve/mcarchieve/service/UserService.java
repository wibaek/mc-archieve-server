package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.dto.SignupDto;
import com.mcarchieve.mcarchieve.dto.UserDto;
import com.mcarchieve.mcarchieve.entity.user.Password;
import com.mcarchieve.mcarchieve.entity.user.Profile;
import com.mcarchieve.mcarchieve.entity.user.User;
import com.mcarchieve.mcarchieve.repository.PasswordRepository;
import com.mcarchieve.mcarchieve.repository.ProfileRepository;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import com.mcarchieve.mcarchieve.type.LoginType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private PasswordRepository passwordRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository, PasswordRepository passwordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDto(user);
    }

    public User signup(SignupDto signupDto) {
        if (userRepository.findByEmail(signupDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // 권한 정보 만들기
//        Authority authority = Authority.builder()
//                .authorityName("ROLE_USER")
//                .build();

        Password password = new Password(null, passwordEncoder.encode(signupDto.getPassword()), null);
        password = passwordRepository.save(password);
        Profile profile = new Profile();
        profile = profileRepository.save(profile);
        User user = new User(null, signupDto.getEmail(), password, profile, null, LoginType.BASIC, null);
        return userRepository.save(user);
    }
}

package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.user.Password;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.user.EmailSignUpRequest;
import com.mcarchieve.mcarchieve.dto.user.MyInfoResponse;
import com.mcarchieve.mcarchieve.repository.PasswordRepository;
import com.mcarchieve.mcarchieve.repository.ProfileRepository;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ProfileRepository profileRepository;
    private PasswordRepository passwordRepository;
    private PasswordEncoder passwordEncoder;

    public MyInfoResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return MyInfoResponse.from(user);
    }

    @Transactional
    public User signUp(EmailSignUpRequest emailSignUpRequest) {
        if (userRepository.findByEmail(emailSignUpRequest.email()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Password password = new Password(passwordEncoder.encode(emailSignUpRequest.password()));
        User user = User.createEmailUser(emailSignUpRequest.email(), password, emailSignUpRequest.nickname());

        return user;
    }
}

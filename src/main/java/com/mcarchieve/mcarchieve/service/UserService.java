package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.user.Password;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.auth.EmailSignUpRequest;
import com.mcarchieve.mcarchieve.dto.user.MyInfoResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MyInfoResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return MyInfoResponse.from(user);
    }

    @Transactional
    public User signUp(EmailSignUpRequest emailSignUpRequest) {
        validateEmail(emailSignUpRequest.email());
        validatePassword(emailSignUpRequest.password());

        Password password = new Password(passwordEncoder.encode(emailSignUpRequest.password()));
        User user = User.createEmailUser(emailSignUpRequest.email(), password, emailSignUpRequest.nickname());
        userRepository.save(user);
        return user;
    }

    private void validateEmail(String email) {
        if (userRepository.findByEmail(email).orElse(null) != null) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (!email.contains("@")) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_FORMAT);
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }
}

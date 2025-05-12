package com.wibaek.mcarchieve.auth.service;

import com.wibaek.mcarchieve.auth.domain.Password;
import com.wibaek.mcarchieve.auth.domain.User;
import com.wibaek.mcarchieve.auth.dto.auth.EmailSignUpRequest;
import com.wibaek.mcarchieve.auth.dto.user.MyInfoResponse;
import com.wibaek.mcarchieve.exception.CustomException;
import com.wibaek.mcarchieve.exception.ErrorCode;
import com.wibaek.mcarchieve.auth.repository.UserRepository;
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

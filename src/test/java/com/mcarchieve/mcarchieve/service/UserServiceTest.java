package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.user.LoginType;
import com.mcarchieve.mcarchieve.domain.user.Password;
import com.mcarchieve.mcarchieve.domain.user.Profile;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.auth.EmailSignUpRequest;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private final String email = "user@mc-archieve.wibaek.com";
    private final String password = "strongPass123!";
    private final String nickname = "닉네임";

    @BeforeEach
    void setUp() {
    }

    @Nested
    class 유저_가입_테스트 {

        @Test
        @Transactional
        void 가입을_성공적으로_완료한다() {
            // given
            EmailSignUpRequest request = new EmailSignUpRequest(email, password, nickname);

            // when
            User response = userService.signUp(request);

            // then
            User createdUser = userRepository.findByEmail(email).orElseThrow();
            Profile createdProfile = createdUser.getProfile();
            assertAll(
                    () -> assertThat(createdUser.getEmail()).isEqualTo(email),
                    () -> assertThat(createdProfile.getNickname()).isEqualTo(nickname),
                    () -> assertThat(createdUser.getLoginType()).isEqualTo(LoginType.BASIC)
            );
        }

        @Test
        @Transactional
        void 가입시_중복_이메일이_있으면_예외_응답을_반환한다() {
            // given
            User existingUser = User.createEmailUser(email, new Password("encoded"), "기존 닉네임");
            userRepository.save(existingUser);

            EmailSignUpRequest request = new EmailSignUpRequest(email, password, nickname);

            // when & then
            assertThatThrownBy(() ->
                    userService.signUp(request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        }

        @Test
        void 가입시_유효하지_않은_이메일로_가입하면_예외_응답을_반환한다() {
            // given
            String invalidEmail = "invalid-email";
            EmailSignUpRequest request = new EmailSignUpRequest(invalidEmail, password, nickname);

            // when & then
            assertThatThrownBy(() ->
                    userService.signUp(request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.INVALID_EMAIL_FORMAT.getMessage());
        }

        @Test
        void 가입시_유효하지_않은_비밀번호로_가입하면_예외_응답을_반환한다() {
            // given
            String shortPassword = "short";
            EmailSignUpRequest request = new EmailSignUpRequest(email, shortPassword, nickname);

            // when & then
            assertThatThrownBy(() ->
                    userService.signUp(request))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.INVALID_PASSWORD_FORMAT.getMessage());
        }
    }
}

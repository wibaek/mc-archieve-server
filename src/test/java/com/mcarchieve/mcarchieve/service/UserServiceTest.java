package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.user.LoginType;
import com.mcarchieve.mcarchieve.domain.user.Password;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.auth.EmailSignUpRequest;
import com.mcarchieve.mcarchieve.dto.user.MyInfoResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private final String validEmail = "user@example.com";
    private final String validPassword = "strongPass";
    private final String nickname = "nickname";

    @BeforeEach
    void setUp() {
        // 공통 stubbing은 각 테스트에서 별도로 설정할 수도 있습니다.
    }

    @Test
    void signUp_success() {
        // given
        when(userRepository.findByEmail(validEmail)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(validPassword)).thenReturn("encodedPwd");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        // when
        User saved = userService.signUp(new EmailSignUpRequest(validEmail, validPassword, nickname));

        // then
        verify(userRepository).save(captor.capture());
        User toSave = captor.getValue();
        assertThat(toSave.getEmail()).isEqualTo(validEmail);
        assertThat(toSave.getLoginType()).isEqualTo(LoginType.BASIC);
        assertThat(toSave.getProfile().getNickname()).isEqualTo(nickname);
        assertThat(toSave.getPassword().getPassword()).isEqualTo("encodedPwd");
        assertThat(saved).isSameAs(toSave);
    }

    @Test
    void signUp_whenEmailAlreadyExists_throwsException() {
        // given
        when(userRepository.findByEmail(validEmail)).thenReturn(Optional.of(mock(User.class)));

        // then
        CustomException ex = assertThrows(CustomException.class,
                () -> userService.signUp(new EmailSignUpRequest(validEmail, validPassword, nickname)));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.EMAIL_ALREADY_EXISTS);
    }

    @Test
    void signUp_whenEmailInvalidFormat_throwsException() {
        // given
        String badEmail = "invalid-email";
        when(userRepository.findByEmail(badEmail)).thenReturn(Optional.empty());

        // then
        CustomException ex = assertThrows(CustomException.class,
                () -> userService.signUp(new EmailSignUpRequest(badEmail, validPassword, nickname)));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_EMAIL_FORMAT);
    }

    @Test
    void signUp_whenPasswordTooShort_throwsException() {
        // given
        String shortPwd = "short";
        when(userRepository.findByEmail(validEmail)).thenReturn(Optional.empty());

        // then
        CustomException ex = assertThrows(CustomException.class,
                () -> userService.signUp(new EmailSignUpRequest(validEmail, shortPwd, nickname)));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_PASSWORD_FORMAT);
    }

    @Test
    void getUserByEmail_success() {
        // given
        User user = User.createEmailUser(validEmail, new Password("encoded"), nickname);
        when(userRepository.findByEmail(validEmail)).thenReturn(Optional.of(user));

        // when
        MyInfoResponse dto = userService.getUserByEmail(validEmail);

        // then
        assertThat(dto.email()).isEqualTo(validEmail);
    }

    @Test
    void getUserByEmail_whenNotFound_throwsRuntimeException() {
        // given
        when(userRepository.findByEmail(validEmail)).thenReturn(Optional.empty());

        // then
        assertThrows(RuntimeException.class,
                () -> userService.getUserByEmail(validEmail));
    }
}
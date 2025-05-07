package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.session.MemberStatus;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.SessionMember;
import com.mcarchieve.mcarchieve.domain.user.Password;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.SessionCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.SessionResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.SessionMemberRepository;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class SessionServiceTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionMemberRepository sessionMemberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionService sessionService;

    private User testUser;
    private final String sessionName = "테스트 세션";

    @BeforeEach
    @Transactional
    void setUp() {
        testUser = userRepository.findByEmail("test@mc-archieve.wibaek.com")
                .orElseGet(() -> {
                    User user = User.createEmailUser(
                            "test@mc-archieve.wibaek.com",
                            new Password("encoded"),
                            "테스트 유저");
                    return userRepository.save(user);
                });
    }

    @Nested
    class 세션_생성_테스트 {

        @Test
        @Transactional
        void 세션을_성공적으로_생성한다() {
            // given
            SessionCreateRequest request = new SessionCreateRequest(sessionName, LocalDate.now(), LocalDate.now());

            // when
            SessionResponse response = sessionService.createSession(request, testUser);

            // then
            Session createdSession = sessionRepository.findById(response.id()).orElseThrow();
            SessionMember sessionMember = sessionMemberRepository.findBySessionAndUser(createdSession, testUser).orElseThrow();
            assertAll(
                    () -> assertThat(createdSession.getName()).isEqualTo(sessionName),
                    () -> assertThat(createdSession.getOwner()).isEqualTo(testUser),
                    () -> assertThat(sessionMember.getStatus()).isEqualTo(MemberStatus.APPROVED)
            );


        }
    }

    @Nested
    class 세션_조회_테스트 {

        private Session testSession;

        @BeforeEach
        @Transactional
        void setUp() {
            // Create a test session
            SessionCreateRequest request = new SessionCreateRequest(sessionName, LocalDate.now(), LocalDate.now());
            SessionResponse response = sessionService.createSession(request, testUser);
            testSession = sessionRepository.findById(response.id()).orElseThrow();
        }

        @Test
        @Transactional
        void 모든_세션을_조회한다() {
            // when
            List<SessionResponse> sessions = sessionService.findAllSessions();

            // then
            assertThat(sessions).isNotEmpty();
            assertThat(sessions.stream().anyMatch(s -> s.id().equals(testSession.getId()))).isTrue();
        }

        @Test
        @Transactional
        void 특정_사용자의_세션을_조회한다() {
            // when
            List<SessionResponse> sessions = sessionService.findAllMySessions(testUser);

            // then
            assertThat(sessions).isNotEmpty();
            assertThat(sessions.stream().anyMatch(s -> s.id().equals(testSession.getId()))).isTrue();
        }

        @Test
        @Transactional
        void 세션_ID로_세션을_조회한다() {
            // when
            SessionResponse session = sessionService.findSessionById(testSession.getId());

            // then
            assertThat(session.id()).isEqualTo(testSession.getId());
            assertThat(session.name()).isEqualTo(testSession.getName());
        }

        @Test
        void 존재하지_않는_세션_ID로_조회하면_예외를_반환한다() {
            // given
            Long nonExistentId = 9999L;

            // when & then
            assertThatThrownBy(() ->
                    sessionService.findSessionById(nonExistentId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.SESSION_NOT_FOUND.getMessage());
        }
    }
}
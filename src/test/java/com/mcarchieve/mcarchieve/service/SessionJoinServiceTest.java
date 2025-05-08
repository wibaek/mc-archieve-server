package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.session.MemberStatus;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.SessionMember;
import com.mcarchieve.mcarchieve.domain.user.Password;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.SessionJoinApplicationsResponse;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class SessionJoinServiceTest {

    @Autowired
    private SessionJoinService sessionJoinService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionMemberRepository sessionMemberRepository;

    @Autowired
    private UserRepository userRepository;

    private User owner;
    private User joinUser;
    private Session session;

    @BeforeEach
    @Transactional
    void setUp() {
        owner = userRepository.findByEmail("owner@mc-archieve.wibaek.com")
                .orElseGet(() -> {
                    User u = User.createEmailUser(
                            "owner@mc-archieve.wibaek.com",
                            new Password("encoded"),
                            "세션 소유자");
                    return userRepository.save(u);
                });

        joinUser = userRepository.findByEmail("joiner@mc-archieve.wibaek.com")
                .orElseGet(() -> {
                    User u = User.createEmailUser(
                            "joiner@mc-archieve.wibaek.com",
                            new Password("encoded"),
                            "가입 요청자");
                    return userRepository.save(u);
                });

        session = sessionRepository.save(new Session(
                "테스트 세션",
                owner,
                LocalDate.now(),
                LocalDate.now()
        ));
    }

    @Nested
    class 세션_가입_요청_테스트 {

        @Test
        @Transactional
        void 세션에_가입_요청을_성공적으로_보낸다() {
            // when
            sessionJoinService.requestToJoinSession(session.getId(), joinUser);

            // then
            Optional<SessionMember> opt = sessionMemberRepository.findBySessionAndUser(session, joinUser);
            assertThat(opt).isPresent();
            assertThat(opt.get().getStatus()).isEqualTo(MemberStatus.PENDING);
        }

        @Test
        void 이미_가입_요청이_있으면_예외를_반환한다() {
            // given
            sessionMemberRepository.save(new SessionMember(session, joinUser, MemberStatus.PENDING));

            // when & then
            assertThatThrownBy(() ->
                    sessionJoinService.requestToJoinSession(session.getId(), joinUser))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.ALREADY_REQUESTED_TO_JOIN_SESSION.getMessage());
        }

        @Test
        void 존재하지_않는_세션에_요청하면_예외를_반환한다() {
            // given
            Long invalidSessionId = 9999L;

            // when & then
            assertThatThrownBy(() ->
                    sessionJoinService.requestToJoinSession(invalidSessionId, joinUser))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.SESSION_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class 가입_요청_조회_테스트 {

        @Test
        @Transactional
        void 세션에_대한_대기중인_가입_요청을_모두_조회한다() {
            // given: 두 명의 가입 요청자
            SessionMember req1 = sessionMemberRepository.save(new SessionMember(session, joinUser));
            User joinUser2 = userRepository.save(User.createEmailUser(
                    "join2@mc-archieve.wibaek.com",
                    new Password("encoded"),
                    "가입자2"));
            SessionMember req2 = sessionMemberRepository.save(new SessionMember(session, joinUser2));
            // 그리고 이미 승인된 멤버
            SessionMember approved = sessionMemberRepository.save(new SessionMember(session, joinUser2, MemberStatus.APPROVED));

            // when
            SessionJoinApplicationsResponse response =
                    sessionJoinService.findJoinRequestsBySessionId(session.getId());

            // then
            // DTO에서 제공하는 가입 요청 목록 크기가 pending 상태의 개수와 같다
            assertThat(response.sessionJoinApplications()).hasSize(
                    sessionMemberRepository.findAllBySessionAndStatus(session, MemberStatus.PENDING).size()
            );
            // 포함된 ID 값 확인
            List<Long> returnedIds = response.sessionJoinApplications()
                    .stream()
                    .map(app -> app.id())
                    .toList();
            assertThat(returnedIds).containsExactlyInAnyOrder(req1.getId(), req2.getId());
        }

        @Test
        void 존재하지_않는_세션으로_조회하면_예외를_반환한다() {
            // given
            Long invalidSessionId = 8888L;

            // when & then
            assertThatThrownBy(() ->
                    sessionJoinService.findJoinRequestsBySessionId(invalidSessionId))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.SESSION_NOT_FOUND.getMessage());
        }
    }

    @Nested
    class 가입_요청_승인_테스트 {

        @Test
        @Transactional
        void 세션_가입_요청을_승인한다() {
            // given
            SessionMember pending = sessionMemberRepository.save(new SessionMember(session, joinUser));

            // when
            sessionJoinService.approveJoinRequest(pending.getId(), owner);

            // then
            SessionMember updated = sessionMemberRepository.findById(pending.getId()).orElseThrow();
            assertThat(updated.getStatus()).isEqualTo(MemberStatus.APPROVED);
        }

        @Test
        void 존재하지_않는_신청을_승인하면_예외를_반환한다() {
            // when & then
            assertThatThrownBy(() ->
                    sessionJoinService.approveJoinRequest(7777L, owner))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.SESSION_REQUEST_NOT_FOUND.getMessage());
        }

        @Test
        void 이미_처리된_신청을_승인하면_예외를_반환한다() {
            // given: 이미 승인된 상태
            SessionMember approved = sessionMemberRepository.save(new SessionMember(session, joinUser, MemberStatus.APPROVED));

            // when & then
            assertThatThrownBy(() ->
                    sessionJoinService.approveJoinRequest(approved.getId(), owner))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.SESSION_REQUEST_NOT_FOUND.getMessage());
        }

        @Test
        void 소유자가_아닌_사용자가_승인하면_예외를_반환한다() {
            // given
            SessionMember pending = sessionMemberRepository.save(new SessionMember(session, joinUser));

            // when & then
            assertThatThrownBy(() ->
                    sessionJoinService.approveJoinRequest(pending.getId(), joinUser))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.ONLY_OWNER_CAN_APPROVE_JOIN_REQUEST.getMessage());
        }
    }

    @Nested
    class 가입_요청_거절_테스트 {

        @Test
        @Transactional
        void 세션_가입_요청을_거절한다() {
            // given
            SessionMember pending = sessionMemberRepository.save(new SessionMember(session, joinUser));

            // when
            sessionJoinService.rejectJoinRequest(pending.getId(), owner);

            // then
            SessionMember updated = sessionMemberRepository.findById(pending.getId()).orElseThrow();
            assertThat(updated.getStatus()).isEqualTo(MemberStatus.REJECTED);
        }

        @Test
        void 존재하지_않는_신청을_거절하면_예외를_반환한다() {
            // when & then
            assertThatThrownBy(() ->
                    sessionJoinService.rejectJoinRequest(6666L, owner))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.SESSION_REQUEST_NOT_FOUND.getMessage());
        }

        @Test
        void 이미_처리된_신청을_거절하면_예외를_반환한다() {
            // given: 이미 거절된 상태
            SessionMember rejected = sessionMemberRepository.save(new SessionMember(session, joinUser, MemberStatus.REJECTED));

            // when & then
            assertThatThrownBy(() ->
                    sessionJoinService.rejectJoinRequest(rejected.getId(), owner))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.SESSION_REQUEST_NOT_FOUND.getMessage());
        }

        @Test
        void 소유자가_아닌_사용자가_거절하면_예외를_반환한다() {
            // given
            SessionMember pending = sessionMemberRepository.save(new SessionMember(session, joinUser));

            // when & then
            assertThatThrownBy(() ->
                    sessionJoinService.rejectJoinRequest(pending.getId(), joinUser))
                    .isInstanceOf(CustomException.class)
                    .hasMessage(ErrorCode.ONLY_OWNER_CAN_APPROVE_JOIN_REQUEST.getMessage());
        }
    }
}
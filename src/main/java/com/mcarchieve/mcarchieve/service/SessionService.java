package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.session.MemberStatus;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.SessionMember;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.SessionCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.SessionResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.SessionMemberRepository;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMemberRepository sessionMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public SessionResponse createSession(SessionCreateRequest sessionCreateRequest, User owner) {
        Session session = sessionCreateRequest.toEntity(owner);
        SessionMember sessionMember = new SessionMember(session, owner, MemberStatus.APPROVED);
        session = sessionRepository.save(session);
        sessionMemberRepository.save(sessionMember);
        return SessionResponse.from(session);
    }

    public List<SessionResponse> findAllSessions() {
        return sessionRepository.findAll()
                .stream()
                .map(SessionResponse::from)
                .collect(Collectors.toList());
    }

    public SessionResponse findSessionById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));

        return SessionResponse.from(session);
    }

    // 세션 참가 신청 관련
    @Transactional
    public void requestToJoinSession(Long sessionId, User user) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));

        // 세션에 이미 가입 요청을 보냈는지 확인
        boolean alreadyRequested = sessionMemberRepository.findBySessionAndUser(session, user).isPresent();
        if (alreadyRequested) {
            throw new CustomException(ErrorCode.ALREADY_REQUESTED_TO_JOIN_SESSION);
        }

        SessionMember sessionMember = new SessionMember(session, user);
        sessionMemberRepository.save(sessionMember);
    }

    @Transactional
    public void approveJoinRequest(Long sessionId, Long userId, User requester) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));

        if (!session.getOwner().getId().equals(requester.getId())) {
            throw new CustomException(ErrorCode.ONLY_OWNER_CAN_APPROVE_JOIN_REQUEST);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        SessionMember sessionMember = sessionMemberRepository.findBySessionAndUser(session, user)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_REQUEST_NOT_FOUND));

        if (!sessionMember.getStatus().equals(MemberStatus.PENDING)) {
            throw new CustomException(ErrorCode.SESSION_REQUEST_NOT_FOUND);
        }

        sessionMember.approveRequest();
        sessionMemberRepository.save(sessionMember);
    }

    @Transactional
    public void rejectJoinRequest(Long sessionId, Long userId, User requester) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));

        // Only session owner can reject requests
        if (!session.getOwner().getId().equals(requester.getId())) {
            throw new CustomException(ErrorCode.ONLY_OWNER_CAN_APPROVE_JOIN_REQUEST);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        SessionMember sessionMember = sessionMemberRepository.findBySessionAndUser(session, user)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_REQUEST_NOT_FOUND));

        if (!sessionMember.getStatus().equals(MemberStatus.PENDING)) {
            throw new CustomException(ErrorCode.SESSION_REQUEST_NOT_FOUND);
        }

        sessionMember.rejectRequest();
        sessionMemberRepository.save(sessionMember);
    }
}

package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.session.MemberStatus;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.SessionMember;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.SessionJoinApplicationsResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.SessionMemberRepository;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionJoinService {

    private final SessionRepository sessionRepository;
    private final SessionMemberRepository sessionMemberRepository;
    private final UserRepository userRepository;


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
    public SessionJoinApplicationsResponse findJoinRequestsBySessionId(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));

        List<SessionMember> sessionMembers = sessionMemberRepository.findAllBySessionAndStatus(session, MemberStatus.PENDING);

        return SessionJoinApplicationsResponse.from(sessionMembers);
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

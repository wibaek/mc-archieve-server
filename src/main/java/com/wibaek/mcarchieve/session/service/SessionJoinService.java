package com.wibaek.mcarchieve.session.service;

import com.wibaek.mcarchieve.session.domain.MemberStatus;
import com.wibaek.mcarchieve.session.domain.Session;
import com.wibaek.mcarchieve.session.domain.SessionMember;
import com.wibaek.mcarchieve.auth.domain.User;
import com.wibaek.mcarchieve.session.dto.application.SessionJoinApplicationsResponse;
import com.wibaek.mcarchieve.exception.CustomException;
import com.wibaek.mcarchieve.exception.ErrorCode;
import com.wibaek.mcarchieve.repository.SessionMemberRepository;
import com.wibaek.mcarchieve.session.SessionRepository;
import com.wibaek.mcarchieve.auth.repository.UserRepository;
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
    public void approveJoinRequest(Long applicationId, User requester) {
        SessionMember sessionMember = findSessionJoinApplication(applicationId);

        Session session = sessionMember.getSession();
        validateSessionOwner(session, requester);

        sessionMember.approveRequest();
        sessionMemberRepository.save(sessionMember);
    }

    @Transactional
    public void rejectJoinRequest(Long applicationId, User requester) {
        SessionMember sessionMember = findSessionJoinApplication(applicationId);

        Session session = sessionMember.getSession();
        validateSessionOwner(session, requester);

        sessionMember.rejectRequest();
        sessionMemberRepository.save(sessionMember);
    }

    private SessionMember findSessionJoinApplication(Long applicationId) {
        SessionMember sessionMember = sessionMemberRepository.findById(applicationId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_REQUEST_NOT_FOUND));

        if (!sessionMember.getStatus().equals(MemberStatus.PENDING)) {
            throw new CustomException(ErrorCode.SESSION_REQUEST_NOT_FOUND);
        }

        return sessionMember;
    }

    private void validateSessionOwner(Session session, User requester) {
        if (!session.getOwner().getId().equals(requester.getId())) {
            throw new CustomException(ErrorCode.ONLY_OWNER_CAN_APPROVE_JOIN_REQUEST);
        }
    }
}

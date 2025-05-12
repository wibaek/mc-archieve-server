package com.mcarchieve.mcarchieve.session.domain;

import com.mcarchieve.mcarchieve.common.entity.BaseEntity;
import com.mcarchieve.mcarchieve.auth.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "session_member")
public class SessionMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus status = MemberStatus.PENDING;

    public SessionMember(Session session, User user) {
        this(session, user, MemberStatus.PENDING);
    }

    public SessionMember(Session session, User user, MemberStatus status) {
        this.session = session;
        this.user = user;
        this.status = status;
    }

    public void approveRequest() {
        this.status = MemberStatus.APPROVED;
    }

    public void rejectRequest() {
        this.status = MemberStatus.REJECTED;
    }

    public boolean isMember() {
        return this.status == MemberStatus.APPROVED;
    }
}
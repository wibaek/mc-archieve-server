package com.wibaek.mcarchieve.session.domain;

import com.wibaek.mcarchieve.common.entity.BaseEntity;
import com.wibaek.mcarchieve.auth.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Session extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "session")
    private List<SessionMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "session")
    private List<Story> stories = new ArrayList<>();

    private LocalDate startDate;
    private LocalDate endDate;

    public Session(String name, User owner, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.owner = owner;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isMember(User user) {
        return this.members.stream()
                .anyMatch(member ->
                        member.getUser().getId().equals(user.getId()) &&
                                member.isMember());
    }
}

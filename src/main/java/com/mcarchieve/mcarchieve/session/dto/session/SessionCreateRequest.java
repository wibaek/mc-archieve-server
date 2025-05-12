package com.mcarchieve.mcarchieve.session.dto.session;

import com.mcarchieve.mcarchieve.session.domain.Session;
import com.mcarchieve.mcarchieve.auth.domain.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SessionCreateRequest(

        @NotNull(message = "세션의 이름을 설정해주세요.")
        String name,

        LocalDate startDate,
        LocalDate endDate

) {

    public Session toEntity(User owner) {
        Session session = new Session(
                name,
                owner,
                startDate,
                endDate
        );
        return session;
    }

}

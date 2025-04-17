package com.mcarchieve.mcarchieve.dto.auth;

import jakarta.validation.constraints.NotNull;

public record EmailSignUpRequest(

        @NotNull(message = "이메일을 입력해주세요.")
        String email,

        @NotNull(message = "비밀번호를 입력해주세요.")
        String password,

        @NotNull(message = "닉네임을 입력해주세요.")
        String nickname
) {
}

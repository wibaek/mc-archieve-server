package com.mcarchieve.mcarchieve.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Auth
    EMAIL_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일 로그인에 실패하였습니다. 이메일 또는 비밀번호를 확인해주세요."),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "유효하지 않은 이메일 형식입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호는 최소 8자 이상이어야 합니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}

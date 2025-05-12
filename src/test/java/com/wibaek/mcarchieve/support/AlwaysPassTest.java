package com.wibaek.mcarchieve.support;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
public class AlwaysPassTest {

    @Test
    void alwaysPass() {
        // 아무런 검증 없이 무조건 성공하는 빈 테스트
    }

    @Configuration
    static class EmptyConfig {
        // 스프링 빈을 전혀 정의하지 않는 빈 설정 클래스
    }
}

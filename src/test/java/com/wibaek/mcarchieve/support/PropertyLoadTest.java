package com.wibaek.mcarchieve.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PropertyLoadTest {

    @Autowired
    Environment env;

    @Test
    void testTestResourcesApplicationYmlIsLoaded() {
        // src/test/resources/application.yml 에만 정의했다고 가정한 프로퍼티
        String testValue = env.getProperty("my.test.prop");
        assertThat(testValue)
                .as("src/test/resources/application.yml 의 my.test.prop 값")
                .isEqualTo("hello-test");
    }
}
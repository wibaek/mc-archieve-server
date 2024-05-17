package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.user.ProfileDto;
import com.mcarchieve.mcarchieve.dto.user.UserDto;
import com.mcarchieve.mcarchieve.service.UserService;
import com.mcarchieve.mcarchieve.type.LoginType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Principal;

@SpringBootTest
@ActiveProfiles("test")
public class UserResourceTest {

    @Mock
    private UserService userService;

    @Mock
    private Principal principal;

    @InjectMocks
    private UserResource userResource;

    @Test
    @DisplayName("내 정보 조회")
    public void testGetMyInfo_success() throws Exception {
        // given
        String email = "test@example.com";
        String nickname = "nickname";
        UserDto mockUserDto = new UserDto(1L,
                email,
                LoginType.BASIC,
                null,
                new ProfileDto(1L, nickname, null));

        when(principal.getName()).thenReturn(email);
        when(userService.getUserByEmail(email)).thenReturn(mockUserDto);

        // when
        ResponseEntity<UserDto> result = userResource.getMyInfo(principal);

        // then
        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(email, result.getBody().getEmail());
        assertEquals(LoginType.BASIC, result.getBody().getLoginType());
        assertEquals(1L, result.getBody().getId());
        assertEquals(nickname, result.getBody().getProfile().getNickname());

        verify(principal).getName();
        verify(userService).getUserByEmail(email);

    }
}

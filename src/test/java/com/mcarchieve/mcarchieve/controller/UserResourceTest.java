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
import org.springframework.test.context.ActiveProfiles;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
                "test@example.com",
                LoginType.BASIC,
                null,
                new ProfileDto(1L, nickname, null));

        when(principal.getName()).thenReturn(email);
        when(userService.getUserByEmail(email)).thenReturn(mockUserDto);
        // when
        UserDto result = userResource.getMyInfo(principal);
        // then
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(LoginType.BASIC, result.getLoginType());
        assertEquals(1L, result.getProfile().getId());
        assertEquals(nickname, result.getProfile().getNickname());

        verify(principal).getName();
        verify(userService).getUserByEmail(email);

    }

}

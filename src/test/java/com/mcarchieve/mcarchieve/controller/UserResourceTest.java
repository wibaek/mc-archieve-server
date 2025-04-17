package com.mcarchieve.mcarchieve.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserResourceTest {

//    @Mock
//    private UserService userService;
//
//    @Mock
//    private Principal principal;
//
//    @InjectMocks
//    private UserResource userResource;
//
//    @Test
//    @DisplayName("내 정보 조회")
//    public void testGetMyInfo_success() throws Exception {
//        // given
//        String email = "test@example.com";
//        String nickname = "nickname";
//        UserDto mockUserDto = new UserDto(1L,
//                email,
//                LoginType.BASIC,
//                null,
//                new ProfileDto(1L, nickname, null));
//
//        when(principal.getName()).thenReturn(email);
//        when(userService.getUserByEmail(email)).thenReturn(mockUserDto);
//
//        // when
//        ResponseEntity<UserDto> result = userResource.getMyInfo(principal);
//
//        // then
//        assertNotNull(result);
//        assertEquals(200, result.getStatusCodeValue());
//        assertEquals(email, result.getBody().getEmail());
//        assertEquals(LoginType.BASIC, result.getBody().getLoginType());
//        assertEquals(1L, result.getBody().getId());
//        assertEquals(nickname, result.getBody().getProfile().getNickname());
//
//        verify(principal).getName();
//        verify(userService).getUserByEmail(email);
//
//    }
}

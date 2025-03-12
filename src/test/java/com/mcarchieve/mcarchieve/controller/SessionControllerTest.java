package com.mcarchieve.mcarchieve.controller;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest(SessionController.class)
@ActiveProfiles("test")
public class SessionControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private JwtService jwtService;
//
//    @MockBean
//    private SessionRepository sessionRepository;
//
//    @MockBean
//    private SessionService sessionService;
//
//    @MockBean
//    private StoryService storyService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @WithMockUser
//    void createSessionTest() throws Exception {
//        SessionRequestDto sessionDto = new SessionRequestDto();
//        sessionDto.setName("Test Session");
//
//        SessionResponseDto createdSessionDto = SessionResponseDto.builder().id(1L).name("Test Session").build();
//        // when
//        when(sessionService.createSession(any(SessionRequestDto.class), any(User.class))).thenReturn(createdSessionDto);
//        when(userRepository.findByEmail(anyString())).thenReturn(java.util.Optional.of(new User()));
//
//        ResultActions actions =
//                mockMvc.perform(post("/v1/sessions")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // 403 방지
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(sessionDto))
//                );
//        // then
//        actions
//                .andExpect(status().isCreated())
//                .andExpect(content().json(objectMapper.writeValueAsString(createdSessionDto)));
//    }
//
//    @Test
//    @WithMockUser
//    void getAllSessionsTest_success() throws Exception {
//        List<Session> sessions = Arrays.asList(new Session(), new Session());
//        List<SessionResponseDto> sessionDtos = sessions.stream().map(SessionResponseDto::fromEntity).toList();
//
//        when(sessionService.findAllSessions()).thenReturn(sessionDtos);
//
//        ResultActions actions = mockMvc.perform(get("/v1/sessions")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//        );
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(sessions.size()));
//    }
//
//    @Test
//    @WithMockUser
//    void getSessionByIdTest_success() throws Exception {
//        // given
//        Long sessionId = 1L;
//        Session session = new Session();
//        session.setId(sessionId);
//
//        when(sessionService.findSessionById(sessionId)).thenReturn(SessionResponseDto.fromEntity(session));
//
//        // when
//        ResultActions actions = mockMvc.perform(get("/v1/sessions/{id}", sessionId)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(sessionId));
//    }
//
//
//    @Test
//    @WithMockUser
//    void getSessionByIdTest_notFound() throws Exception {
//        // given
//        Long nonExistentSessionId = 999L;
//
//        when(sessionService.findSessionById(nonExistentSessionId)).thenReturn(null);
//
//        // when
//        ResultActions actions = mockMvc.perform(get("/v1/sessions/{id}", nonExistentSessionId)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isNotFound());
//    }
//
//
//    @Test
//    @WithMockUser
//    void deleteSessionTest_success() throws Exception {
//        // given
//        Long sessionId = 1L;
//        Session existingSession = new Session();
//        existingSession.setId(sessionId);
//
//        when(sessionService.deleteSessionById(sessionId)).thenReturn(true);
//
//        // when
//        ResultActions actions = mockMvc.perform(delete("/v1/sessions/{id}", sessionId)
//                .with(SecurityMockMvcRequestPostProcessors.csrf()) // 403 방지
//                .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @WithMockUser
//    void deleteSessionTest_notFound() throws Exception {
//        // given
//        Long nonExistentSessionId = 999L; // 존재하지 않는 세션 ID 설정
//
//        when(sessionService.deleteSessionById(nonExistentSessionId)).thenReturn(false);
//
//        // when
//        ResultActions actions = mockMvc.perform(delete("/v1/sessions/{id}", nonExistentSessionId)
//                .with(SecurityMockMvcRequestPostProcessors.csrf()) // 403 방지
//                .accept(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isNotFound());
//
//        verify(sessionRepository, never()).deleteById(nonExistentSessionId);
//    }
}

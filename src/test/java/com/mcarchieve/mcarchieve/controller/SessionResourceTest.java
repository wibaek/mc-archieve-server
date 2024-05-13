package com.mcarchieve.mcarchieve.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mcarchieve.mcarchieve.entity.session.Session;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.dto.SessionDto;
import com.mcarchieve.mcarchieve.service.JwtService;

@WebMvcTest(SessionResource.class)
public class SessionResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionRepository sessionRepository;

    @MockBean
    private JwtService jwtService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("세션 생성 테스트")
    void reateSessionTest() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Test Session");

        Session session = sessionDto.toEntity();
        Session savedSession = new Session();
        savedSession.setId(1L);
        savedSession.setName(session.getName());
        // when
        when(sessionRepository.save(any(Session.class))).thenReturn(savedSession);

        ResultActions actions =
                mockMvc.perform(post("/sessions")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()) // 403 방지
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto))
                );
        // then
        actions
                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "http://localhost/sessions/1"))
                .andExpect(content().json(objectMapper.writeValueAsString(savedSession)));

        verify(sessionRepository).save(any(Session.class));
    }

    @Test
    @WithMockUser
    void getAllSessionsTest_success() throws Exception {
        List<Session> sessions = Arrays.asList(new Session(), new Session());

        when(sessionRepository.findAll()).thenReturn(sessions);

        ResultActions actions = mockMvc.perform(get("/sessions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sessions)));
    }

    @Test
    @WithMockUser
    void getSessionByIdTest_success() throws Exception {
        // given
        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // when
        ResultActions actions = mockMvc.perform(get("/sessions/{id}", sessionId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(session)));
    }


    @Test
    @WithMockUser
    void getSessionByIdTest_notFound() throws Exception {
        // given
        Long nonExistentSessionId = 999L;

        when(sessionRepository.findById(nonExistentSessionId)).thenReturn(Optional.empty());

        // when
        ResultActions actions = mockMvc.perform(get("/sessions/{id}", nonExistentSessionId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser
    void deleteSessionTest() throws Exception {
        // given
        Long sessionId = 1L;
        Session existingSession = new Session();
        existingSession.setId(sessionId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(existingSession));
        doNothing().when(sessionRepository).deleteById(sessionId);

        // when
        ResultActions actions = mockMvc.perform(delete("/sessions/{id}", sessionId)
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // 403 방지
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isNoContent());
        verify(sessionRepository).deleteById(sessionId);
    }

    @Test
    @WithMockUser
    void deleteSessionTest_notFound() throws Exception {
        // given
        Long nonExistentSessionId = 999L; // 존재하지 않는 세션 ID 설정

        when(sessionRepository.existsById(nonExistentSessionId)).thenReturn(false);

        // when
        ResultActions actions = mockMvc.perform(delete("/sessions/{id}", nonExistentSessionId)
                .with(SecurityMockMvcRequestPostProcessors.csrf()) // 403 방지
                .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isNotFound());

        verify(sessionRepository, never()).deleteById(nonExistentSessionId);
    }
}

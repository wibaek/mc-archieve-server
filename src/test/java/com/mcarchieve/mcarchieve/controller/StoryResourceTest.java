package com.mcarchieve.mcarchieve.controller;

import com.mcarchieve.mcarchieve.dto.session.StoryResponseDto;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.Story;
import com.mcarchieve.mcarchieve.service.JwtService;
import com.mcarchieve.mcarchieve.service.StoryService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(StoryResource.class)
@ActiveProfiles("test")
public class StoryResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private StoryService storyService;

    private String imageRepositoryUri = "https://test-image-provider/";

    @Test
    @WithMockUser
    void getStoriesByIdTest_success() throws Exception {
        // given
        Long id = 1L;
        Session session = new Session();
        Story story = new Story();
        story.setId(id);
        story.setSession(session);

        when(storyService.findStoryById(id)).thenReturn(StoryResponseDto.fromEntity(story, imageRepositoryUri));
        // when
        ResultActions actions = mockMvc.perform(get("/v1/stories/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }
}

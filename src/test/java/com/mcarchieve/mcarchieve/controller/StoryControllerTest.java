package com.mcarchieve.mcarchieve.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;


@WebMvcTest(StoryController.class)
@ActiveProfiles("test")
public class StoryControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
////    @MockBean
////    private JwtService jwtService;
//
//    @MockBean
//    private StoryService storyService;
//
////    private String imageRepositoryUri = "https://test-image-provider/";
//
//    @Test
////    @WithMockUser
//    void getStoriesByIdTest_success() throws Exception {
//        // given
//        Long id = 1L;
//        Session session = new Session();
//        Story story = new Story();
//        story.setId(id);
//        story.setSession(session);
//
//        when(storyService.findStoryById(id)).thenReturn(StoryResponseDto.fromEntity(story, imageRepositoryUri));
//        // when
//        ResultActions actions = mockMvc.perform(get("/v1/stories/" + id)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(id));
//    }
}

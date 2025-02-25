package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.Image;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.Story;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.StoryCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.StoryResponse;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.StoryRepository;
import com.mcarchieve.mcarchieve.service.image.FileUploadPath;
import com.mcarchieve.mcarchieve.service.image.ImageStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final SessionRepository sessionRepository;
    private final ImageStorageService imageStorageService;

    @Transactional
    public StoryResponse createStory(StoryCreateRequest storyCreateRequest, MultipartFile imageFile, User user) {
        Session session = sessionRepository.findById(storyCreateRequest.sessionId())
                .orElseThrow(() -> new RuntimeException("<" + storyCreateRequest.sessionId() + ">는 유효하지 않은 세션 ID 입니다."));

        Image image = imageStorageService.storeImage(imageFile, FileUploadPath.STORY);

        Story story = storyCreateRequest.toEntity(image, user, session);
        story = storyRepository.save(story);

        return StoryResponse.from(story);
    }


//    public StoryResponse findStoryById(Long id) {
//        Story story = storyRepository.findById(id).orElseThrow(() -> new RuntimeException("Story not found"));
//
//        return StoryResponse.fromEntity(story, imageRepositoryUri);
//    }
//
//    public List<StoryResponseDto> findStoriesBySessionId(Long id) {
//        List<Story> stories = storyRepository.findBySessionId(id);
//        List<StoryResponseDto> storyResponseDtos = new java.util.ArrayList<>();
//
//        for (Story story : stories) {
//            StoryResponseDto storyResponseDto = StoryResponseDto.fromEntity(story, imageRepositoryUri);
//            storyResponseDtos.add(storyResponseDto);
//        }
//
//        return storyResponseDtos;
//    }


}

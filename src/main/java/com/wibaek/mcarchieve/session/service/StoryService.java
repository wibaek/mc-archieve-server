package com.wibaek.mcarchieve.session.service;

import com.wibaek.mcarchieve.common.entity.Image;
import com.wibaek.mcarchieve.session.domain.Session;
import com.wibaek.mcarchieve.session.domain.Story;
import com.wibaek.mcarchieve.auth.domain.User;
import com.wibaek.mcarchieve.session.dto.story.StoryBulkCreateResponse;
import com.wibaek.mcarchieve.session.dto.story.StoryResponse;
import com.wibaek.mcarchieve.exception.CustomException;
import com.wibaek.mcarchieve.exception.ErrorCode;
import com.wibaek.mcarchieve.session.SessionRepository;
import com.wibaek.mcarchieve.repository.StoryRepository;
import com.wibaek.mcarchieve.common.service.image.FileUploadPath;
import com.wibaek.mcarchieve.common.service.image.ImageStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final SessionRepository sessionRepository;
    private final ImageStorageService imageStorageService;

    @Value("${cloud.cloudflare.r2.url}")
    private String storageUrl;

    @Transactional
    public StoryResponse createStory(Long sessionId, MultipartFile imageFile, User user, String caption) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));

        if (!session.isMember(user)) {
            throw new CustomException(ErrorCode.NOT_SESSION_MEMBER);
        }

        Image image = imageStorageService.storeImage(imageFile, FileUploadPath.STORY);

        Story story = new Story(caption, image, user, session);
        story = storyRepository.save(story);

        return StoryResponse.from(story, storageUrl);
    }

    @Transactional
    public StoryBulkCreateResponse createStories(Long sessionId, List<MultipartFile> imageFiles, User user) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));

        if (!session.isMember(user)) {
            throw new CustomException(ErrorCode.NOT_SESSION_MEMBER);
        }

        List<Story> stories = imageFiles.stream()
                .map(imageFile -> {
                    Image image = imageStorageService.storeImage(imageFile, FileUploadPath.STORY);
                    return new Story(null, image, user, session);
                })
                .collect(Collectors.toList());

        stories = storyRepository.saveAll(stories);

        return StoryBulkCreateResponse.from(stories, storageUrl);

    }

    public StoryResponse findStoryById(Long id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("스토리를 찾을 수 없습니다."));

        return StoryResponse.from(story, storageUrl);
    }

    public List<StoryResponse> findStoriesBySessionId(Long id) {
        return storyRepository.findBySessionId(id)
                .stream()
                .map(story -> StoryResponse.from(story, storageUrl))
                .collect(Collectors.toList());
    }
}

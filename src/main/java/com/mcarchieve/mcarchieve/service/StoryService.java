package com.mcarchieve.mcarchieve.service;

import com.mcarchieve.mcarchieve.domain.Image;
import com.mcarchieve.mcarchieve.domain.session.Session;
import com.mcarchieve.mcarchieve.domain.session.SessionMember;
import com.mcarchieve.mcarchieve.domain.session.Story;
import com.mcarchieve.mcarchieve.domain.user.User;
import com.mcarchieve.mcarchieve.dto.session.StoryCreateRequest;
import com.mcarchieve.mcarchieve.dto.session.StoryResponse;
import com.mcarchieve.mcarchieve.exception.CustomException;
import com.mcarchieve.mcarchieve.exception.ErrorCode;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.StoryRepository;
import com.mcarchieve.mcarchieve.service.image.FileUploadPath;
import com.mcarchieve.mcarchieve.service.image.ImageStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public StoryResponse createStory(StoryCreateRequest storyCreateRequest, MultipartFile imageFile, User user) {
        Session session = sessionRepository.findById(storyCreateRequest.sessionId())
                .orElseThrow(() -> new CustomException(ErrorCode.SESSION_NOT_FOUND));

        if (!session.isMember(user)) {
            throw new CustomException(ErrorCode.NOT_SESSION_MEMBER);
        }

        Image image = imageStorageService.storeImage(imageFile, FileUploadPath.STORY);

        Story story = storyCreateRequest.toEntity(image, user, session);
        story = storyRepository.save(story);

        return StoryResponse.from(story);
    }
    
    public StoryResponse findStoryById(Long id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("스토리를 찾을 수 없습니다."));

        return StoryResponse.from(story);
    }

    public List<StoryResponse> findStoriesBySessionId(Long id) {
        return storyRepository.findBySessionId(id)
                .stream()
                .map(StoryResponse::from)
                .collect(Collectors.toList());
    }
}

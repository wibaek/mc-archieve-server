package com.mcarchieve.mcarchieve.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.mcarchieve.mcarchieve.dto.session.StoryRequestDto;
import com.mcarchieve.mcarchieve.dto.session.StoryResponseDto;
import com.mcarchieve.mcarchieve.entity.Image;
import com.mcarchieve.mcarchieve.entity.session.Session;
import com.mcarchieve.mcarchieve.entity.session.Story;
import com.mcarchieve.mcarchieve.entity.user.User;
import com.mcarchieve.mcarchieve.repository.SessionRepository;
import com.mcarchieve.mcarchieve.repository.StoryRepository;
import com.mcarchieve.mcarchieve.repository.UserRepository;
import com.mcarchieve.mcarchieve.type.FileUploadPath;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class StoryService {
    private StoryRepository storyRepository;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    private S3Service s3Service;

    public StoryService(StoryRepository storyRepository,
                        UserRepository userRepository,
                        SessionRepository sessionRepository,
                        S3Service s3Service) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.s3Service = s3Service;
    }

    public List<StoryResponseDto> findStoriesBySessionId(Long id) {
        List<Story> stories = storyRepository.findBySessionId(id);
        List<StoryResponseDto> storyResponseDtos = new java.util.ArrayList<>();

        for (Story story : stories) {
            StoryResponseDto storyResponseDto = new StoryResponseDto(story);
            storyResponseDtos.add(storyResponseDto);
        }

        return storyResponseDtos;
    }


    public Story createStory(StoryRequestDto storyRequestDto) throws IOException {
        Story story = new Story();
        User createdUser = userRepository.findById(storyRequestDto.getCreatedById()).orElseThrow(() -> new RuntimeException("User not found"));
        Session session = sessionRepository.findById(storyRequestDto.getSessionId()).orElseThrow(() -> new RuntimeException("Session not found"));

        MultipartFile file = storyRequestDto.getImage();

        if (file != null) {
            ObjectMetadata metadata = new ObjectMetadata();

            UUID uuid = UUID.randomUUID();
            String originalFilename = file.getOriginalFilename();
            String fileName = uuid.toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
            String path = FileUploadPath.STORY.getPath() + "/" + fileName;

            Image image = s3Service.uploadFile(file, path);
            story.setImage(image);
        }

        story.setId(null);
        story.setDescription(storyRequestDto.getDescription());
        story.setCreatedBy(createdUser);
        story.setSession(session);

        return storyRepository.save(story);
    }
}

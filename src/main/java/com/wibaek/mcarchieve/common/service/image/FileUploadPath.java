package com.wibaek.mcarchieve.common.service.image;

public enum FileUploadPath {
    STORY("story/"),
    PROFILE("profile/");

    private final String path;

    FileUploadPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

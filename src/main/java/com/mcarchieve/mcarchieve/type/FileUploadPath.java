package com.mcarchieve.mcarchieve.type;

public enum FileUploadPath {
    STORY("story");

    private String path;

    FileUploadPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

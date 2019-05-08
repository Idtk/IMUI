package com.gengqiquan.imui.model;

public class ImVideo {
    private String path;
    private ImImage image;

    public ImVideo(String path, ImImage image) {
        this.path = path;
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public ImImage getImage() {
        return image;
    }
}

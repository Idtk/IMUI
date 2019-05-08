package com.gengqiquan.imui.model;

public class ImVideo {
    private Object video;
    private ImImage image;

    public ImVideo(Object video, ImImage image) {
        this.video = video;
        this.image = image;
    }

    public Object getVideo() {
        return video;
    }

    public ImImage getImage() {
        return image;
    }
}

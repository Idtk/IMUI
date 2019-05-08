package com.gengqiquan.imui.interfaces;

import java.util.List;

public interface IMsgBuildPolicy {
    Object buildTextMessage(String message);

    Object buildAudioMessage(String recordPath, int duration);

    Object buildImgMessage(List<String> paths);

    Object buildVideoMessage(String imgPath, String videoPath, int imgWidth, int imgHeight, long duration);
}
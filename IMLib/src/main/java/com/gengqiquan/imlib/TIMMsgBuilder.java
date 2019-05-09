package com.gengqiquan.imlib;

import com.gengqiquan.imui.interfaces.IMsgBuildPolicy;
import com.tencent.imsdk.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TIMMsgBuilder implements IMsgBuildPolicy {
    @NotNull
    @Override
    public TIMMessage buildTextMessage(@NotNull String message) {
        TIMMessage TimMsg = new TIMMessage();
        TIMTextElem ele = new TIMTextElem();
        ele.setText(message);
        TimMsg.addElement(ele);
        return TimMsg;
    }

    @NotNull
    @Override
    public TIMMessage buildAudioMessage(@NotNull String recordPath, int duration) {
        TIMMessage TimMsg = new TIMMessage();
        TIMSoundElem ele = new TIMSoundElem();
        ele.setDuration(duration / 1000);
        ele.setPath(recordPath);
        TimMsg.addElement(ele);
        return TimMsg;
    }

    @NotNull
    @Override
    public TIMMessage buildImgMessage(@NotNull List<String> paths) {
        TIMMessage TimMsg = new TIMMessage();
        for (String path : paths) {
            TIMImageElem elem = new TIMImageElem();
            elem.setPath(path);
            TimMsg.addElement(elem);
        }
        return TimMsg;
    }

    @Override
    public Object buildCustomMessage(String json) {
        TIMMessage TimMsg = new TIMMessage();
        TIMCustomElem ele = new TIMCustomElem();
        ele.setData(json.getBytes());
        TimMsg.addElement(ele);
        return TimMsg;
    }

    @Override
    public TIMMessage buildVideoMessage(String imgPath, String videoPath, int width, int height, long duration) {
        TIMMessage TimMsg = new TIMMessage();
        TIMVideoElem ele = new TIMVideoElem();

        TIMVideo video = new TIMVideo();
        video.setDuaration(duration / 1000);
        video.setType("mp4");
        TIMSnapshot snapshot = new TIMSnapshot();

        snapshot.setWidth(width);
        snapshot.setHeight(height);
        ele.setSnapshot(snapshot);
        ele.setVideo(video);
        ele.setSnapshotPath(imgPath);
        ele.setVideoPath(videoPath);

        TimMsg.addElement(ele);

        return TimMsg;
    }


}

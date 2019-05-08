package com.gengqiquan.imlib.audio;

import android.net.Uri;
import com.gengqiquan.imui.interfaces.IMsgBuildPolicy;
import com.tencent.imsdk.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class TIMMsgBuilder implements IMsgBuildPolicy {
    @NotNull
    @Override
    public TIMMessage buildTextMessage(@NotNull String message) {
        TIMMessage TIMMsg = new TIMMessage();
        TIMTextElem ele = new TIMTextElem();
        ele.setText(message);
        TIMMsg.addElement(ele);
        return TIMMsg;
    }

    @NotNull
    @Override
    public TIMMessage buildAudioMessage(@NotNull String recordPath, int duration) {
        TIMMessage TIMMsg = new TIMMessage();
        TIMSoundElem ele = new TIMSoundElem();
        ele.setDuration(duration / 1000);
        ele.setPath(recordPath);
        TIMMsg.addElement(ele);
        return TIMMsg;
    }

    @NotNull
    @Override
    public TIMMessage buildImgMessage(@NotNull List<String> paths) {
        TIMMessage TIMMsg = new TIMMessage();
        for (String path : paths) {
            TIMImageElem elem = new TIMImageElem();
            elem.setPath(path);
            TIMMsg.addElement(elem);
        }
        return TIMMsg;
    }

    @Override
    public TIMMessage buildVideoMessage(String imgPath, String videoPath, int width, int height, long duration) {
        TIMMessage TIMMsg = new TIMMessage();
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

        TIMMsg.addElement(ele);

        return TIMMsg;
    }


}

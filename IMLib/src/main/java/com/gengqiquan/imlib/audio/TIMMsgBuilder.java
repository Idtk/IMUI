package com.gengqiquan.imlib.audio;

import com.gengqiquan.imui.interfaces.IMsgBuildPolicy;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMTextElem;
import org.jetbrains.annotations.NotNull;

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


}

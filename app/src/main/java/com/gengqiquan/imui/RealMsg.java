package com.gengqiquan.imui;

import com.tencent.imsdk.*;
import org.jetbrains.annotations.NotNull;

public class RealMsg implements IimMsg {
    TIMElem txMsg;
    boolean isSelf;
    String time;

    public RealMsg(TIMElem txMsg, boolean isSelf) {
        this.txMsg = txMsg;
        this.isSelf = isSelf;
    }

    public RealMsg(TIMElem txMsg, boolean isSelf, String time) {
        this.txMsg = txMsg;
        this.isSelf = isSelf;
        this.time = time;
    }

    @Override
    public String text() {
        return ((TIMTextElem) txMsg).getText();
    }

    @Override
    public ImImage img() {
        TIMImage timImage = ((TIMImageElem) txMsg).getImageList().get(0);
        return new ImImage(timImage.getUrl(), timImage.getWidth(), timImage.getHeight());
    }

    @NotNull
    @Override
    public String video() {
        return null;
    }

    @NotNull
    @Override
    public String sound() {
        return null;
    }

    @NotNull
    @Override
    public String time() {
        return time;
    }

    @Override
    public int uiType() {
        switch (txMsg.getType()) {
            case Text:
                return 1;
            case Image:
                return 2;
            case Video:
                return 3;
            case Sound:
                return 4;
            default:
                return 0;
        }
    }


    @Override
    public TIMElem realData() {
        return txMsg;
    }

    @Override
    public boolean isSelf() {
        return isSelf;
    }


}

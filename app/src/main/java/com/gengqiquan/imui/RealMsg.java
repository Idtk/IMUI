package com.gengqiquan.imui;

import android.annotation.SuppressLint;
import androidx.annotation.MainThread;
import com.tencent.imsdk.*;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RealMsg implements IimMsg {
    TIMElem txMsg;
    boolean isSelf;
    Date time;

    public RealMsg(TIMElem txMsg, boolean isSelf) {
        this.txMsg = txMsg;
        this.isSelf = isSelf;
    }

    public RealMsg(TIMElem txMsg, boolean isSelf, Date time) {
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

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat formatYear = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat formatMonth = new SimpleDateFormat("MM-dd HH:mm");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat formatHours = new SimpleDateFormat("HH:mm");

    @MainThread
    public static String format(Date record) {
        record.setYear(record.getYear());
        Date now = new Date();
        if (now.getYear() != record.getYear()) {
            return formatYear.format(record);
        }
        int days = now.getDay() - record.getDay();
        if (days == 0) {
            return formatHours.format(record);
        }
        if (days == 1) {
            return "昨天 " + formatHours.format(record);
        }
        if (days == 2) {
            return "前天 " + formatHours.format(record);
        }
        return formatMonth.format(record);
    }

    @NotNull
    @Override
    public String time() {
        return format(time);
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

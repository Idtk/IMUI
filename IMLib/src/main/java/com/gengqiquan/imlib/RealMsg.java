package com.gengqiquan.imlib;

import android.annotation.SuppressLint;
import androidx.annotation.MainThread;
import com.gengqiquan.imui.interfaces.IimMsg;
import com.gengqiquan.imui.model.ImImage;
import com.tencent.imsdk.*;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.tencent.imsdk.TIMElemType.Sound;

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
        return ((TIMSoundElem) txMsg).getPath();
    }

    @Override
    public long duration() {
        if (Sound == txMsg.getType()) {
            return ((TIMSoundElem) txMsg).getDuration();
        }
        return 0;
    }

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat formatYear = new SimpleDateFormat("yyyy年MM月dd日");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat formatMonth = new SimpleDateFormat("MM-dd HH:mm");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat formatHours = new SimpleDateFormat("HH:mm");

    @MainThread
    public static String format(Date record) {
        Date now = new Date();
//        if (now.getYear() != record.getYear()) {
//            return formatYear.format(record) + tag + formatHours.format(record);
//        }
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
        if (days <= 7) {
            Calendar c = Calendar.getInstance();
            c.setTime(record);
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            return "星期" + weekStr[weekday] + " " + formatHours.format(record);
        }
        return formatYear.format(record) + " " + formatHours.format(record);
    }

    final static String[] weekStr = new String[]{"日", "一", "二", "三", "四", "五", "六",};

    @NotNull
    @Override
    public String time() {
        if (time != null)
            return format(time);
        return null;
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

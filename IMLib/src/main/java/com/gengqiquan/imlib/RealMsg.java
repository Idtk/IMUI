package com.gengqiquan.imlib;

import android.annotation.SuppressLint;
import androidx.annotation.MainThread;
import com.gengqiquan.imui.interfaces.IimMsg;
import com.gengqiquan.imui.model.ImImage;
import com.tencent.imsdk.*;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RealMsg implements IimMsg {
    TIMMessage timMsg;
    Date time;
    TIMElem elem;


    private RealMsg(TIMMessage timMsg, TIMElem elem, Date time) {
        this.timMsg = timMsg;
        this.elem = elem;
        this.time = time;
    }

    public static List<RealMsg> create(TIMMessage timMsg) {
        List<RealMsg> list = new ArrayList<>();
        if (timMsg.getElementCount() == 0L) {
            return list;
        }
        for (int i = 0; i < timMsg.getElementCount(); i++) {
            TIMElem ele = timMsg.getElement(i);
            Date time = i == timMsg.getElementCount() - 1 ? new Date(timMsg.timestamp() * 1000) : null;
            list.add(new RealMsg(timMsg, ele, time));
        }
        return list;
    }

    @Override
    public String text() {
        if (elem.getType() != TIMElemType.Text) {
            throw new IllegalArgumentException("can not call img() that is not of the type: Text");
        }
        return ((TIMTextElem) elem).getText();
    }

    @Override
    public ImImage img() {
        if (elem.getType() != TIMElemType.Image) {
            throw new IllegalArgumentException("can not call img() that is not of the type: image");
        }
        TIMImage timImage = ((TIMImageElem) elem).getImageList().get(0);
        return new ImImage(timImage.getUrl(), timImage.getWidth(), timImage.getHeight());
    }

    @NotNull
    @Override
    public String video() {
        if (elem.getType() != TIMElemType.Video) {
            throw new IllegalArgumentException("can not call img() that is not of the type: Video");
        }
        return null;
    }


    @NotNull
    @Override
    public String sound() {
        if (elem.getType() != TIMElemType.Sound) {
            throw new IllegalArgumentException("can not call img() that is not of the type: sound");
        }
        return ((TIMSoundElem) elem).getPath();
    }

    @Override
    public long duration() {
        if (elem.getType() != TIMElemType.Sound) {
            throw new IllegalArgumentException("can not call img() that is not of the type: sound");
        }
        return ((TIMSoundElem) elem).getDuration();
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
        switch (elem.getType()) {
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
    public TIMMessage realData() {
        return timMsg;
    }

    @Override
    public boolean isSelf() {
        return timMsg.isSelf();
    }


}

package com.gengqiquan.imlib.video.listener;

import android.graphics.Bitmap;

/**
 * @author gengqiquan
 * @date 2019-05-08 11:50
 */
public interface MediaTakeListener {

    void captureSuccess(Bitmap bitmap);

    void recordSuccess(String url, Bitmap firstFrame, long duration);

}

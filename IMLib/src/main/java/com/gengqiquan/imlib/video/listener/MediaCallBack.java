package com.gengqiquan.imlib.video.listener;


import android.content.Intent;

/**
 * @author gengqiquan
 * @date 2019-05-08 11:48
 */

public interface MediaCallBack {

    void onImageSuccess(String path);

    void onVideoSuccess(Intent videoData);
}

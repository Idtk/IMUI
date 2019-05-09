/**
 * @file
 * @brief 后台任务管理器
 * @author rjlee
 * @date 2014年12月6日
 * Modify List:
 * 2014年12月6日 rjlee create it.
 */
package com.gengqiquan.imui.help;

import android.os.Handler;
import android.os.Looper;


public class BackgroundTasks {

    private volatile Handler mHandler = new Handler(Looper.getMainLooper());


    public void runOnUiThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    public boolean postDelayed(Runnable r, long delayMillis) {
        return mHandler.postDelayed(r, delayMillis);
    }

    public Handler getHandler() {
        return mHandler;
    }

    private static BackgroundTasks instance;

    public static BackgroundTasks getInstance() {
        if (instance == null) {
            synchronized (BackgroundTasks.class) {
                if (instance == null) {
                    instance = new BackgroundTasks();
                }
            }
        }
        return instance;
    }
}

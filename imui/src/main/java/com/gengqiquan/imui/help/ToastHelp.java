package com.gengqiquan.imui.help;

import android.widget.Toast;

/**
 * toas辅助类
 */
public class ToastHelp {

    private static Toast mToast;

    public static final void toastLongMessage(final String message) {
        BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(IMHelp.getAppContext(), message,
                        Toast.LENGTH_LONG);
                mToast.show();
            }
        });
    }


    public static final void toastShortMessage(final String message) {
        BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(IMHelp.getAppContext(), message,
                        Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }


}

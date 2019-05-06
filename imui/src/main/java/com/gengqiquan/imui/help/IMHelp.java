package com.gengqiquan.imui.help;

import android.content.Context;
import com.gengqiquan.imui.interfaces.IAudioRecorder;
import com.gengqiquan.imui.interfaces.IMsgBuildPolicy;

import java.io.File;
import java.util.UUID;

/**
 * 文件路径提供辅助类
 *
 * @author gengqiquan
 * @date 2019/4/3 4:02 PM
 */
public class IMHelp {
    final static String TAG = "MediaHelp";


    private static final long LIMIT_SIZE = 100 * 1024 * 1024;//100MB防止后面压缩没有空间了
    private static IMsgBuildPolicy msgBuildPolicy;

    public static IMsgBuildPolicy getMsgBuildPolicy() {
        return msgBuildPolicy;
    }

    private static IAudioRecorder audioRecord;

    public static IAudioRecorder getAudioRecorder() {
        return audioRecord;
    }

    public static File getAudioPath() {
        return pathProvider.audioDir("audio");
    }

    public static int getAudioRecordMaxTime() {
        return 60;
    }

    public static int getVideoRecordMaxTime() {
        return 10;
    }


    public static void init(Context context, IAudioRecorder recorder, IMsgBuildPolicy buildPolicy) {
        if (pathProvider == null) {
            synchronized (IMHelp.class) {
                if (pathProvider == null) {
                    applicationContext = context;
                    pathProvider = new PathProvider(applicationContext);
                    msgBuildPolicy = buildPolicy;
                    audioRecord = recorder;
                }
            }
        }
    }

    private static Context applicationContext;

    public static Context getAppContext() {
        return applicationContext;
    }

    private static volatile PathProvider pathProvider;

    private static class PathProvider {
        final File CACHE_DIR;

        private PathProvider(Context context) {
            CACHE_DIR = context.getCacheDir();
        }

        File photoDir(Context context) {
            return context.getDir("photo", Context.MODE_PRIVATE);
        }

        File audioDir(String type) {
            File file = new File(CACHE_DIR, type + File.separator);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }

        private String randomString() {
            String time = System.currentTimeMillis() + "";
            String id = UUID.randomUUID().toString();//生成的id942cd30b-16c8-449e-8dc5-028f38495bb5中间含有横杠
            id = id.replace("-", "");//替换掉中间的那个斜杠
            return MD5.md5(id + time);
        }

    }
}

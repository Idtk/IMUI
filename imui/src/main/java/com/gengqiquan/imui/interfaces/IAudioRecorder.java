package com.gengqiquan.imui.interfaces;

public interface IAudioRecorder {
    void startRecord(AudioRecordComplete callback);

    void stopRecord();

    String getRecordAudioPath();

    int getDuration();


}

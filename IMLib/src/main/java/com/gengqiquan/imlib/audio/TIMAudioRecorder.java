package com.gengqiquan.imlib.audio;

import com.gengqiquan.imui.interfaces.AudioRecordComplete;
import com.gengqiquan.imui.interfaces.IAudioRecorder;

public class TIMAudioRecorder implements IAudioRecorder {
    @Override
    public void startRecord(final AudioRecordComplete callback) {
        UIKitAudioArmMachine.getInstance().startRecord(new UIKitAudioArmMachine.AudioRecordCallback() {
            @Override
            public void recordComplete(long duration) {
                callback.recordComplete(duration);
            }
        });
    }

    @Override
    public void stopRecord() {
        UIKitAudioArmMachine.getInstance().stopRecord();
    }


    @Override
    public String getRecordAudioPath() {
        return UIKitAudioArmMachine.getInstance().getRecordAudioPath();
    }

    @Override
    public int getDuration() {
        return UIKitAudioArmMachine.getInstance().getDuration();
    }
}

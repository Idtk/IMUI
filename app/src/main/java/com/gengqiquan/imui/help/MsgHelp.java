package com.gengqiquan.imui.help;

import android.net.Uri;
import com.gengqiquan.imui.model.MessageInfo;
import com.tencent.imsdk.*;

import java.io.File;
import java.util.List;

/**
 * Created by valexhuang on 2018/8/6.
 */

public class MsgHelp {

    public static final String GROUP_CREATE = "group_create";
    public static final String GROUP_DELETE = "group_delete";

    public static TIMMessage buildTextMessage(String message) {
        TIMMessage TIMMsg = new TIMMessage();
        TIMTextElem ele = new TIMTextElem();
        ele.setText(message);
        TIMMsg.addElement(ele);
        return TIMMsg;
    }

    public static MessageInfo buildCustomFaceMessage(int groupId, String faceName) {
        MessageInfo info = new MessageInfo();
        TIMMessage TIMMsg = new TIMMessage();
        TIMFaceElem ele = new TIMFaceElem();
        ele.setIndex(groupId);
        ele.setData(faceName.getBytes());
        TIMMsg.addElement(ele);
        info.setExtra("[自定义表情]");
        info.setMsgTime(System.currentTimeMillis());
        info.setSelf(true);
        info.setTIMMessage(TIMMsg);
        info.setFromUser(TIMManager.getInstance().getLoginUser());
        info.setMsgType(MessageInfo.MSG_TYPE_CUSTOM_FACE);
        return info;
    }


//    public static MessageInfo buildImageMessage(final Uri uri, boolean compressed, boolean appPohto) {
//        final MessageInfo info = new MessageInfo();
//        final TIMImageElem ele = new TIMImageElem();
//        //不是应用自己拍摄的照片，先copy一份过来
//        if (!appPohto) {
//            ImageUtil.CopyImageInfo copInfo = ImageUtil.copyImage(uri, UIKitConstants.IMAGE_DOWNLOAD_DIR);
//            if (copInfo == null)
//                return null;
//            ele.setPath(copInfo.getPath());
//            info.setDataPath(copInfo.getPath());
//            info.setImgWithd(copInfo.getWidth());
//            info.setImgHeight(copInfo.getHeight());
//            info.setDataUri(FileUtil.getUriFromPath(copInfo.getPath()));
//        } else {
//            info.setDataUri(uri);
//            int size[] = ImageUtil.getImageSize(uri);
//            String path = FileUtil.getPathFromUri(uri);
//            ele.setPath(path);
//            info.setDataPath(path);
//            info.setImgWithd(size[0]);
//            info.setImgHeight(size[1]);
//        }
//
//        TIMMessage TIMMsg = new TIMMessage();
//        TIMMessageExt ext = new TIMMessageExt(TIMMsg);
//        ext.setSender(TIMManager.getInstance().getLoginUser());
//        ext.setTimestamp(System.currentTimeMillis());
//        if (!compressed)
//            ele.setLevel(0);
//        TIMMsg.addElement(ele);
//        info.setSelf(true);
//        info.setTIMMessage(TIMMsg);
//        info.setExtra("[图片]");
//        info.setMsgTime(System.currentTimeMillis());
//        info.setFromUser(TIMManager.getInstance().getLoginUser());
//        info.setMsgType(MessageInfo.MSG_TYPE_IMAGE);
//        return info;
//    }

    public static MessageInfo buildVideoMessage(String imgPath, String videoPath, int width, int height, long duration) {
        MessageInfo info = new MessageInfo();
        TIMMessage TIMMsg = new TIMMessage();
        TIMVideoElem ele = new TIMVideoElem();

        TIMVideo video = new TIMVideo();
        video.setDuaration(duration / 1000);
        video.setType("mp4");
        TIMSnapshot snapshot = new TIMSnapshot();

        snapshot.setWidth(width);
        snapshot.setHeight(height);
        ele.setSnapshot(snapshot);
        ele.setVideo(video);
        ele.setSnapshotPath(imgPath);
        ele.setVideoPath(videoPath);

        TIMMsg.addElement(ele);
        Uri videoUri = Uri.fromFile(new File(videoPath));
        info.setSelf(true);
        info.setImgWithd(width);
        info.setImgHeight(height);
        info.setDataPath(imgPath);
        info.setDataUri(videoUri);
        info.setTIMMessage(TIMMsg);
        info.setExtra("[视频]");
        info.setMsgTime(System.currentTimeMillis());
        info.setFromUser(TIMManager.getInstance().getLoginUser());
        info.setMsgType(MessageInfo.MSG_TYPE_VIDEO);
        return info;
    }

    public static TIMMessage buildAudioMessage(String recordPath, int duration) {
        TIMMessage TIMMsg = new TIMMessage();
        TIMSoundElem ele = new TIMSoundElem();
        ele.setDuration(duration / 1000);
        ele.setPath(recordPath);
        TIMMsg.addElement(ele);
        return TIMMsg;
    }

    public static TIMMessage buildImgMessage(List<String> paths) {
        TIMMessage TIMMsg = new TIMMessage();
        for (String path : paths) {
            TIMImageElem elem = new TIMImageElem();
            elem.setPath(path);
            TIMMsg.addElement(elem);
        }
        return TIMMsg;
    }


    public static MessageInfo buildReadNoticeMessage(String peer) {
        MessageInfo info = new MessageInfo();
        info.setPeer(peer);
        info.setMsgType(MessageInfo.MSG_STATUS_READ);
        return info;
    }

    public static MessageInfo buildGroupCustomMessage(String action, String message) {
        MessageInfo info = new MessageInfo();
        TIMMessage TIMMsg = new TIMMessage();
        TIMCustomElem ele = new TIMCustomElem();
        ele.setData(action.getBytes());
        ele.setExt(message.getBytes());
        TIMMsg.addElement(ele);
        info.setSelf(true);
        info.setTIMMessage(TIMMsg);
        info.setExtra(message);
        info.setMsgTime(System.currentTimeMillis());
        if (action.equals(GROUP_CREATE)) {
            info.setMsgType(MessageInfo.MSG_TYPE_GROUP_CREATE);
        } else if (action.equals(GROUP_DELETE)) {
            info.setMsgType(MessageInfo.MSG_TYPE_GROUP_DELETE);
        }
        return info;
    }

    public static MessageInfo buildGroupTipsMessage(String peer, int type, String message) {
        MessageInfo info = new MessageInfo();
        info.setSelf(true);
        info.setMsgType(type);
        info.setPeer(peer);
        info.setExtra(message);
        info.setMsgTime(System.currentTimeMillis());
        return info;
    }


    public static int TIMElemType2MessageInfoType(TIMElemType type) {
        switch (type) {
            case Text:
                return MessageInfo.MSG_TYPE_TEXT;
            case Image:
                return MessageInfo.MSG_TYPE_IMAGE;
            case Sound:
                return MessageInfo.MSG_TYPE_AUDIO;
            case Video:
                return MessageInfo.MSG_TYPE_VIDEO;
            case File:
                return MessageInfo.MSG_TYPE_FILE;
            case Location:
                return MessageInfo.MSG_TYPE_LOCATION;
            case Face:
                return MessageInfo.MSG_TYPE_CUSTOM_FACE;
            case GroupTips:
                return MessageInfo.MSG_TYPE_TIPS;
        }

        return -1;
    }

    public static interface CopyHandler {
        void copyComplete(MessageInfo messageInfo);
    }
}

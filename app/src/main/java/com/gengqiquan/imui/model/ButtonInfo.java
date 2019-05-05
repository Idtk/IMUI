package com.gengqiquan.imui.model;

public class ButtonInfo {
    int res;
    String text;
    int type;

    public int getRes() {
        return res;
    }

    public String getText() {
        return text;
    }

    public int getType() {
        return type;
    }

    public ButtonInfo(String text, int res, int type) {
        this.res = res;
        this.text = text;
        this.type = type;
    }
}

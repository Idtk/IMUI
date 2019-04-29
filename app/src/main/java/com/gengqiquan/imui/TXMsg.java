package com.gengqiquan.imui;

public class TXMsg {
    String text;
    String url;
    Type type;

    enum Type {
        TEXT, IMG, VIDEO, SHARE
    }

    public TXMsg(String text, String url, Type type) {
        this.text = text;
        this.url = url;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

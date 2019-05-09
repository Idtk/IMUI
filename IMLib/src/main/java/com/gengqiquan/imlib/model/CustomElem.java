package com.gengqiquan.imlib.model;

import com.gengqiquan.imlib.uitls.JsonUtil;

public class CustomElem {


    private CustomType type = CustomType.invalid;
    private Platform platform;
    private Object data;

    public CustomElem(CustomType type, Platform platform, Object data) {
        this.type = type;
        this.platform = platform;
        this.data = data;
    }

    public CustomType getType() {
        return type;
    }

    public void setType(CustomType type) {
        this.type = type;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static CustomElem create(String json) {

        CustomElem customElem = (CustomElem) JsonUtil.fromJson(json, CustomElem.class);
        String data = JsonUtil.getString(json, "data");
        if (customElem.type == null) {
            customElem.type = CustomType.invalid;
        }
        switch (customElem.type) {
            case share:
                customElem.data = JsonUtil.fromJson(data, ShareElem.class);
                break;
            default:
        }
        return customElem;
    }

    @Override
    public String toString() {
        return "CustomElem{" +
                "type='" + type + '\'' +
                ", platform=" + platform.toString() +
                ", data=" + data.toString() +
                '}';
    }
}

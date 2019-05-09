package com.gengqiquan.imlib.model;

public class Platform {

    private String from;
    private String os;
    private String version;
    private String device_info;
    private String from_user_id;
    private String receive_user_id;
    private String lat;
    private String lng;

    public Platform(String from, String os, String version, String device_info, String from_user_id, String receive_user_id, String lat, String lng) {
        this.from = from;
        this.os = os;
        this.version = version;
        this.device_info = device_info;
        this.from_user_id = from_user_id;
        this.receive_user_id = receive_user_id;
        this.lat = lat;
        this.lng = lng;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getReceive_user_id() {
        return receive_user_id;
    }

    public void setReceive_user_id(String receive_user_id) {
        this.receive_user_id = receive_user_id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "from='" + from + '\'' +
                ", os='" + os + '\'' +
                ", version='" + version + '\'' +
                ", device_info='" + device_info + '\'' +
                ", from_user_id='" + from_user_id + '\'' +
                ", receive_user_id='" + receive_user_id + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}

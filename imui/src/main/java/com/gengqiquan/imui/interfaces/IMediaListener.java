package com.gengqiquan.imui.interfaces;

public interface IMediaListener {
    void loading(long now, long total);

    void ready(String path);

    void start();

    void error();

}

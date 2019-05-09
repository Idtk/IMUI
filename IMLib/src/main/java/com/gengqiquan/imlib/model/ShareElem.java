package com.gengqiquan.imlib.model;

public class ShareElem {
    private String router_url;
    private int action_type;
    private int style;
    private Msg msg;

    public ShareElem(String router_url, int action_type, int style, Msg msg) {
        this.router_url = router_url;
        this.action_type = action_type;
        this.style = style;
        this.msg = msg;
    }

    public String getRouter_url() {
        return router_url;
    }

    public void setRouter_url(String router_url) {
        this.router_url = router_url;
    }

    public int getAction_type() {
        return action_type;
    }

    public void setAction_type(int action_type) {
        this.action_type = action_type;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ShareElem{" +
                "router_url='" + router_url + '\'' +
                ", action_type=" + action_type +
                ", style=" + style +
                ", msg=" + msg.toString() +
                '}';
    }

    public static class Msg {
        private String title;
        private String content;
        private String module;
        private String pic_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        @Override
        public String toString() {
            return "Msg{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", module='" + module + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    '}';
        }
    }
}

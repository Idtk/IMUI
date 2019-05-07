package com.gengqiquan.imui.model;

public class MenuAction {
    String text;
    Action action;

    public MenuAction(String text, Action action) {
        this.text = text;
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public String getText() {
        return text;
    }

    public interface Action {
        void action(Object data);
    }
}

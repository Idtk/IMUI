package com.gengqiquan.imui.model;

public class MenuAction {
    String text;
    Action action;
    boolean onlySelf;

    public MenuAction(String text, Action action) {
        this.text = text;
        this.action = action;
        this.onlySelf = false;
    }

    public MenuAction(String text, Action action, boolean onlySelf) {
        this.text = text;
        this.action = action;
        this.onlySelf = onlySelf;
    }

    public Action getAction() {
        return action;
    }

    public String getText() {
        return text;
    }

    public boolean isOnlySelf() {
        return onlySelf;
    }

    public interface Action {
        void action(Object data);
    }
}

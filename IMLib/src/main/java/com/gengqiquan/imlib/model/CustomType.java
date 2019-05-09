package com.gengqiquan.imlib.model;

public enum CustomType {
    share("share"),

    invalid("invalid");

    private String key;

    CustomType(String s) {
        this.key = s;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static boolean support(String key) {
        CustomType[] bSkeys = values();
        for (CustomType value : bSkeys) {
            if (value.key.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
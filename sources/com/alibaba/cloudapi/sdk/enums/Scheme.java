package com.alibaba.cloudapi.sdk.enums;

/* loaded from: classes2.dex */
public enum Scheme {
    HTTP("HTTP://"),
    HTTPS("HTTPS://"),
    WEBSOCKET("WS://");

    String value;

    Scheme(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }
}

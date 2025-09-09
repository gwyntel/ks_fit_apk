package com.alibaba.android.multiendinonebridge;

/* loaded from: classes2.dex */
public enum ErrorCode {
    ERROR_LOADING_LIBRARY(-2, "internal error, the so library did not load successfully"),
    NOT_INITIALIZED_YET(-3, "Not initialized yet"),
    INVALID_PARAMETERS(-4, "Invalid parameters");

    private final int code;
    private final String description;

    ErrorCode(int i2, String str) {
        this.code = i2;
        this.description = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.code + ": " + this.description;
    }
}

package com.alibaba.sdk.android.openaccount.message;

/* loaded from: classes2.dex */
public class Message implements Cloneable {
    public String action;
    public int code;
    public String message;
    public String type;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

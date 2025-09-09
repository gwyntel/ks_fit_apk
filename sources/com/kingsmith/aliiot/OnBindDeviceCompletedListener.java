package com.kingsmith.aliiot;

/* loaded from: classes4.dex */
public interface OnBindDeviceCompletedListener {
    void onFailed(int i2, String str, String str2);

    void onFailed(Exception exc);

    void onSuccess(String str);

    void printLog(String str);
}

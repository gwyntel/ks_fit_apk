package com.alibaba.sdk.android.openaccount.ui.model;

/* loaded from: classes2.dex */
public class CaptchaModel {
    public String csessionid;
    public String nctoken;
    public String sig;

    public CaptchaModel(String str, String str2, String str3) {
        this.sig = str;
        this.csessionid = str2;
        this.nctoken = str3;
    }
}

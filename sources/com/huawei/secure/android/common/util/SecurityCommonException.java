package com.huawei.secure.android.common.util;

/* loaded from: classes4.dex */
public class SecurityCommonException extends Exception {

    /* renamed from: c, reason: collision with root package name */
    private static final long f18529c = 1;

    /* renamed from: a, reason: collision with root package name */
    private String f18530a;

    /* renamed from: b, reason: collision with root package name */
    private String f18531b;

    public SecurityCommonException() {
    }

    public String getMsgDes() {
        return this.f18531b;
    }

    public String getRetCd() {
        return this.f18530a;
    }

    public SecurityCommonException(Throwable th) {
        super(th);
    }

    public SecurityCommonException(String str, Throwable th) {
        super(str, th);
    }

    public SecurityCommonException(String str) {
        super(str);
        this.f18531b = str;
    }

    public SecurityCommonException(String str, String str2) {
        this.f18530a = str;
        this.f18531b = str2;
    }
}

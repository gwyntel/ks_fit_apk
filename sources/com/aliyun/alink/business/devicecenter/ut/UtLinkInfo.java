package com.aliyun.alink.business.devicecenter.ut;

/* loaded from: classes2.dex */
public class UtLinkInfo {

    /* renamed from: a, reason: collision with root package name */
    public String f10627a;

    /* renamed from: b, reason: collision with root package name */
    public String f10628b;

    /* renamed from: c, reason: collision with root package name */
    public String f10629c;

    /* renamed from: d, reason: collision with root package name */
    public String f10630d;

    /* renamed from: e, reason: collision with root package name */
    public String f10631e;

    public UtLinkInfo() {
    }

    public String getConnectionTime() {
        return this.f10630d;
    }

    public String getErrorCode() {
        return this.f10631e;
    }

    public String getLinkType() {
        return this.f10629c;
    }

    public String getProductKey() {
        return this.f10628b;
    }

    public String getUserId() {
        return this.f10627a;
    }

    public void setConnectionTime(String str) {
        this.f10630d = str;
    }

    public void setErrorCode(String str) {
        this.f10631e = str;
    }

    public void setLinkType(String str) {
        this.f10629c = str;
    }

    public void setProductKey(String str) {
        this.f10628b = str;
    }

    public void setUserId(String str) {
        this.f10627a = str;
    }

    public String toString() {
        return "UtLinkInfo{user_id='" + this.f10627a + "', product_key='" + this.f10628b + "', link_type='" + this.f10629c + "', connection_time='" + this.f10630d + "', error_code='" + this.f10631e + "'}";
    }

    public UtLinkInfo(String str, String str2, String str3) {
        this.f10627a = str;
        this.f10628b = str2;
        this.f10629c = str3;
    }

    public UtLinkInfo(String str, String str2, String str3, String str4) {
        this.f10627a = str;
        this.f10630d = str2;
        this.f10628b = str3;
        this.f10629c = str4;
    }
}

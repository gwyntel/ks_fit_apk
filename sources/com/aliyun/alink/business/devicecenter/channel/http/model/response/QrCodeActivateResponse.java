package com.aliyun.alink.business.devicecenter.channel.http.model.response;

import com.aliyun.alink.business.devicecenter.channel.http.model.DataObject;

/* loaded from: classes2.dex */
public class QrCodeActivateResponse extends DataObject {

    /* renamed from: a, reason: collision with root package name */
    public final String f10325a;

    /* renamed from: b, reason: collision with root package name */
    public final String f10326b;

    public static class QrCodeActivateResponseBuilder {

        /* renamed from: a, reason: collision with root package name */
        public String f10327a;

        /* renamed from: b, reason: collision with root package name */
        public String f10328b;

        public QrCodeActivateResponse build() {
            return new QrCodeActivateResponse(this.f10327a, this.f10328b);
        }

        public String toString() {
            return "QrCodeActivateResponse.QrCodeActivateResponseBuilder(uuid=" + this.f10327a + ", userId=" + this.f10328b + ")";
        }

        public QrCodeActivateResponseBuilder userId(String str) {
            this.f10328b = str;
            return this;
        }

        public QrCodeActivateResponseBuilder uuid(String str) {
            this.f10327a = str;
            return this;
        }
    }

    public QrCodeActivateResponse(String str, String str2) {
        this.f10325a = str;
        this.f10326b = str2;
    }

    public static QrCodeActivateResponseBuilder builder() {
        return new QrCodeActivateResponseBuilder();
    }

    public String getUserId() {
        return this.f10326b;
    }

    public String getUuid() {
        return this.f10325a;
    }
}

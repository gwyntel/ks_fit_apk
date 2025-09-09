package com.aliyun.alink.business.devicecenter.channel.http.model.response;

import com.aliyun.alink.business.devicecenter.channel.http.model.DataObject;

/* loaded from: classes2.dex */
public class QrCodeStaticBindResponse extends DataObject {

    /* renamed from: a, reason: collision with root package name */
    public String f10329a;

    /* renamed from: b, reason: collision with root package name */
    public String f10330b;

    /* renamed from: c, reason: collision with root package name */
    public int f10331c;

    public static class QrCodeStaticBindResponseBuilder {

        /* renamed from: a, reason: collision with root package name */
        public String f10332a;

        /* renamed from: b, reason: collision with root package name */
        public String f10333b;

        /* renamed from: c, reason: collision with root package name */
        public int f10334c;

        public QrCodeStaticBindResponse build() {
            return new QrCodeStaticBindResponse(this.f10332a, this.f10333b, this.f10334c);
        }

        public QrCodeStaticBindResponseBuilder code(int i2) {
            this.f10334c = i2;
            return this;
        }

        public QrCodeStaticBindResponseBuilder device_id(String str) {
            this.f10332a = str;
            return this;
        }

        public QrCodeStaticBindResponseBuilder message(String str) {
            this.f10333b = str;
            return this;
        }

        public String toString() {
            return "QrCodeStaticBindResponse.QrCodeStaticBindResponseBuilder(device_id=" + this.f10332a + ", message=" + this.f10333b + ", code=" + this.f10334c + ")";
        }
    }

    public QrCodeStaticBindResponse(String str, String str2, int i2) {
        this.f10329a = str;
        this.f10330b = str2;
        this.f10331c = i2;
    }

    public static QrCodeStaticBindResponseBuilder builder() {
        return new QrCodeStaticBindResponseBuilder();
    }

    public int getCode() {
        return this.f10331c;
    }

    public String getDevice_id() {
        return this.f10329a;
    }

    public String getMessage() {
        return this.f10330b;
    }

    public void setCode(int i2) {
        this.f10331c = i2;
    }

    public void setDevice_id(String str) {
        this.f10329a = str;
    }

    public void setMessage(String str) {
        this.f10330b = str;
    }
}

package com.aliyun.alink.business.devicecenter.channel.http.mtop.request;

import com.aliyun.alink.linksdk.connectsdk.BaseApiRequest;

/* loaded from: classes2.dex */
public class RtosDeviceBindRequestV2 extends BaseApiRequest {
    public ModelV2 deviceBindRequest;
    public String API_NAME = "mtop.alibaba.ai.iot.bindDevice";
    public String VERSION = "1.0";
    public boolean NEED_ECODE = true;
    public boolean NEED_SESSION = true;
    public String authInfo = null;

    public static class ModelV2 {

        /* renamed from: a, reason: collision with root package name */
        public String f10340a = null;

        /* renamed from: b, reason: collision with root package name */
        public String f10341b = null;

        /* renamed from: c, reason: collision with root package name */
        public Param f10342c = null;

        public String getNetworkType() {
            return this.f10341b;
        }

        public Param getParams() {
            return this.f10342c;
        }

        public String getProductKey() {
            return this.f10340a;
        }

        public void setNetworkType(String str) {
            this.f10341b = str;
        }

        public void setParams(Param param) {
            this.f10342c = param;
        }

        public void setProductKey(String str) {
            this.f10340a = str;
        }
    }

    public static class Param {

        /* renamed from: a, reason: collision with root package name */
        public String f10343a;

        /* renamed from: b, reason: collision with root package name */
        public String f10344b;

        /* renamed from: c, reason: collision with root package name */
        public String f10345c;

        /* renamed from: d, reason: collision with root package name */
        public String f10346d;

        /* renamed from: e, reason: collision with root package name */
        public String f10347e;

        public String getClientId() {
            return this.f10346d;
        }

        public String getCode() {
            return this.f10347e;
        }

        public String getDeviceName() {
            return this.f10343a;
        }

        public String getProductKey() {
            return this.f10344b;
        }

        public String getToken() {
            return this.f10345c;
        }

        public void setClientId(String str) {
            this.f10346d = str;
        }

        public void setCode(String str) {
            this.f10347e = str;
        }

        public void setDeviceName(String str) {
            this.f10343a = str;
        }

        public void setProductKey(String str) {
            this.f10344b = str;
        }

        public void setToken(String str) {
            this.f10345c = str;
        }
    }

    public String getAPI_NAME() {
        return this.API_NAME;
    }

    public String getAuthInfo() {
        return this.authInfo;
    }

    public ModelV2 getDeviceBindRequest() {
        return this.deviceBindRequest;
    }

    public String getVERSION() {
        return this.VERSION;
    }

    public boolean isNEED_ECODE() {
        return this.NEED_ECODE;
    }

    public boolean isNEED_SESSION() {
        return this.NEED_SESSION;
    }

    public void setAPI_NAME(String str) {
        this.API_NAME = str;
    }

    public void setAuthInfo(String str) {
        this.authInfo = str;
    }

    public void setDeviceBindRequest(ModelV2 modelV2) {
        this.deviceBindRequest = modelV2;
    }

    public void setNEED_ECODE(boolean z2) {
        this.NEED_ECODE = z2;
    }

    public void setNEED_SESSION(boolean z2) {
        this.NEED_SESSION = z2;
    }

    public void setVERSION(String str) {
        this.VERSION = str;
    }
}

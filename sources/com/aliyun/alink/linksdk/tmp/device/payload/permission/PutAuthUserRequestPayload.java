package com.aliyun.alink.linksdk.tmp.device.payload.permission;

import com.aliyun.alink.linksdk.tmp.device.payload.CommonRequestPayload;

/* loaded from: classes2.dex */
public class PutAuthUserRequestPayload extends CommonRequestPayload<PutAuthUserRequestParam> {

    public static class PutAuthUserRequestParam {
        protected String deviceName;
        protected String prodKey;
        protected String sessionKey;
        protected String token;
        protected String uid;

        public String getDeviceName() {
            return this.deviceName;
        }

        public String getProdKey() {
            return this.prodKey;
        }

        public String getSessionKey() {
            return this.sessionKey;
        }

        public String getToken() {
            return this.token;
        }

        public String getUid() {
            return this.uid;
        }

        public void setDeviceName(String str) {
            this.deviceName = str;
        }

        public void setProdKey(String str) {
            this.prodKey = str;
        }

        public void setSessionKey(String str) {
            this.sessionKey = str;
        }

        public void setToken(String str) {
            this.token = str;
        }

        public void setUid(String str) {
            this.uid = str;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.aliyun.alink.linksdk.tmp.device.payload.permission.PutAuthUserRequestPayload$PutAuthUserRequestParam] */
    public PutAuthUserRequestPayload(String str, String str2) {
        super(str, str2);
        ?? putAuthUserRequestParam = new PutAuthUserRequestParam();
        this.params = putAuthUserRequestParam;
        putAuthUserRequestParam.setProdKey(str);
        ((PutAuthUserRequestParam) this.params).setDeviceName(str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAuthId(String str) {
        ((PutAuthUserRequestParam) this.params).setUid(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.tmp.device.payload.CommonRequestPayload
    public void setSessionKey(String str) {
        super.setSessionKey(str);
        ((PutAuthUserRequestParam) this.params).setSessionKey(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setToken(String str) {
        ((PutAuthUserRequestParam) this.params).setToken(str);
    }
}

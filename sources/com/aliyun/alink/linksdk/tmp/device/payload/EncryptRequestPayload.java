package com.aliyun.alink.linksdk.tmp.device.payload;

/* loaded from: classes2.dex */
public class EncryptRequestPayload<T> {
    protected CommonRequestPayload<T> data;
    protected Extra extra;

    public static class Extra {
        String sessionKey;
        String uid;

        public String getSessionKey() {
            return this.sessionKey;
        }

        public String getUid() {
            return this.uid;
        }

        public void setSessionKey(String str) {
            this.sessionKey = str;
        }

        public void setUid(String str) {
            this.uid = str;
        }
    }

    public CommonRequestPayload<T> getData() {
        return this.data;
    }

    public Extra getExtra() {
        return this.extra;
    }

    public void setData(CommonRequestPayload<T> commonRequestPayload) {
        this.data = commonRequestPayload;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }
}

package com.aliyun.alink.linksdk.alcs.coap;

/* loaded from: classes2.dex */
public class AlcsCoAPContext {
    protected String mAddress;
    protected long mContextId;
    protected int mPort;
    protected int mWaitTime;

    public String getAddress() {
        return this.mAddress;
    }

    public long getContextId() {
        return this.mContextId;
    }

    public int getPort() {
        return this.mPort;
    }

    public int getWaitTime() {
        return this.mWaitTime;
    }

    public void setAddress(String str) {
        this.mAddress = str;
    }

    public void setContextId(long j2) {
        this.mContextId = j2;
    }

    public void setPort(int i2) {
        this.mPort = i2;
    }

    public void setWaitTime(int i2) {
        this.mWaitTime = i2;
    }
}

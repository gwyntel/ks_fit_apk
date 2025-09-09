package com.aliyun.alink.linksdk.alcs.api.client;

/* loaded from: classes2.dex */
public class AlcsClientConfig<ExtraParams> {
    protected String mAccessKey;
    protected String mAccessToken;
    public int mConnectKeepType;
    public String mDataFormat;
    protected String mDeviceName;
    protected String mDstAddr;
    protected int mDstPort;
    public ExtraParams mExtraParams;
    protected int mHeartBeatTimeout = 60000;
    protected boolean mIsNeddAuth;
    public String mPluginId;
    protected String mProductKey;

    public String getAccessKey() {
        return this.mAccessKey;
    }

    public String getAccessToken() {
        return this.mAccessToken;
    }

    public String getDeviceName() {
        return this.mDeviceName;
    }

    public String getDstAddr() {
        return this.mDstAddr;
    }

    public int getDstPort() {
        return this.mDstPort;
    }

    public String getProductKey() {
        return this.mProductKey;
    }

    public boolean isNeddAuth() {
        return this.mIsNeddAuth;
    }

    public void setAccessKey(String str) {
        this.mAccessKey = str;
    }

    public void setAccessToken(String str) {
        this.mAccessToken = str;
    }

    public void setDeviceName(String str) {
        this.mDeviceName = str;
    }

    public void setDstAddr(String str) {
        this.mDstAddr = str;
    }

    public void setDstPort(int i2) {
        this.mDstPort = i2;
    }

    public void setIsNeddAuth(boolean z2) {
        this.mIsNeddAuth = z2;
    }

    public void setProductKey(String str) {
        this.mProductKey = str;
    }
}

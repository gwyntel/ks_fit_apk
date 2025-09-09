package com.aliyun.alink.linksdk.cmp.connect.alcs;

import com.aliyun.alink.linksdk.cmp.core.base.AConnectConfig;

/* loaded from: classes2.dex */
public class AlcsConnectConfig<ExtraConnectParams> extends AConnectConfig {
    protected String iotId;
    protected boolean isSecurity;
    protected String mAccessKey;
    protected String mAccessToken;
    public int mConnectKeepType;
    public String mDataFormat;
    protected String mDeviceName;
    protected String mDstAddr;
    protected int mDstPort = 0;
    public ExtraConnectParams mExtraConnectParams;
    public String mPluginId;
    protected String mProductKey;

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnectConfig
    public boolean checkVaild() {
        return true;
    }

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

    public String getIotId() {
        return this.iotId;
    }

    public String getProductKey() {
        return this.mProductKey;
    }

    public boolean isNeedAuthInfo() {
        return false;
    }

    public boolean isSecurity() {
        return this.isSecurity;
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

    public void setIotId(String str) {
        this.iotId = str;
    }

    public void setProductKey(String str) {
        this.mProductKey = str;
    }

    public void setSecurity(boolean z2) {
        this.isSecurity = z2;
    }
}

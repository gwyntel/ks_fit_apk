package com.aliyun.alink.linksdk.cmp.connect.alcs;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectConfig;

/* loaded from: classes2.dex */
public class AlcsServerConnectConfig extends AConnectConfig {
    private String blackClients;
    private String deviceName;
    private String prefix;
    private String productKey;
    private String secret;

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnectConfig
    public boolean checkVaild() {
        return (TextUtils.isEmpty(this.productKey) || TextUtils.isEmpty(this.deviceName)) ? false : true;
    }

    public String getBlackClients() {
        return this.blackClients;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public String getSecret() {
        return this.secret;
    }

    public boolean isNeedAuthInfo() {
        return TextUtils.isEmpty(this.prefix) || TextUtils.isEmpty(this.secret);
    }

    public void setBlackClients(String str) {
        this.blackClients = str;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public void setProductKey(String str) {
        this.productKey = str;
    }

    public void setSecret(String str) {
        this.secret = str;
    }
}

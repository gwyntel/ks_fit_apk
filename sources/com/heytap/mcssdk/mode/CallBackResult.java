package com.heytap.mcssdk.mode;

import com.heytap.msp.push.mode.BaseMode;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class CallBackResult extends BaseMode {
    private static final String SPLITTER = "&";
    private String mAppKey;
    private String mAppPackage;
    private String mAppSecret;
    private int mCommand;
    private String mContent;
    private String mRegisterID;
    private int mResponseCode = -2;
    private String mSdkVersion;

    public static <T> String parseToString(List<T> list) {
        StringBuilder sb = new StringBuilder();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append("&");
        }
        return sb.toString();
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public String getAppPackage() {
        return this.mAppPackage;
    }

    public String getAppSecret() {
        return this.mAppSecret;
    }

    public int getCommand() {
        return this.mCommand;
    }

    public String getContent() {
        return this.mContent;
    }

    public String getRegisterID() {
        return this.mRegisterID;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public String getSdkVersion() {
        return this.mSdkVersion;
    }

    @Override // com.heytap.msp.push.mode.BaseMode
    public int getType() {
        return 4105;
    }

    public void setAppKey(String str) {
        this.mAppKey = str;
    }

    public void setAppPackage(String str) {
        this.mAppPackage = str;
    }

    public void setAppSecret(String str) {
        this.mAppSecret = str;
    }

    public void setCommand(int i2) {
        this.mCommand = i2;
    }

    public void setContent(String str) {
        this.mContent = str;
    }

    public void setRegisterID(String str) {
        this.mRegisterID = str;
    }

    public void setResponseCode(int i2) {
        this.mResponseCode = i2;
    }

    public void setSdkVersion(String str) {
        this.mSdkVersion = str;
    }

    public String toString() {
        return "CallBackResult{, mRegisterID='" + this.mRegisterID + "', mSdkVersion='" + this.mSdkVersion + "', mCommand=" + this.mCommand + "', mContent='" + this.mContent + "', mAppPackage=" + this.mAppPackage + "', mResponseCode=" + this.mResponseCode + '}';
    }
}

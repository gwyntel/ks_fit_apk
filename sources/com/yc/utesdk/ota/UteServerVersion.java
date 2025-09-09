package com.yc.utesdk.ota;

/* loaded from: classes4.dex */
public class UteServerVersion {
    public String btName;
    public String description;
    public String fileUrl;
    public int flag;
    public String forceUpdate;
    public String publicTime;
    public String versionName;

    public UteServerVersion(int i2) {
        this.flag = i2;
    }

    public String getBtName() {
        return this.btName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFileUrl() {
        return this.fileUrl;
    }

    public int getFlag() {
        return this.flag;
    }

    public String getForceUpdate() {
        return this.forceUpdate;
    }

    public String getPublicTime() {
        return this.publicTime;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setBtName(String str) {
        this.btName = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setFileUrl(String str) {
        this.fileUrl = str;
    }

    public void setFlag(int i2) {
        this.flag = i2;
    }

    public void setForceUpdate(String str) {
        this.forceUpdate = str;
    }

    public void setPublicTime(String str) {
        this.publicTime = str;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }

    public UteServerVersion(int i2, String str, String str2, String str3, String str4, String str5, String str6) {
        this.flag = i2;
        this.btName = str;
        this.versionName = str2;
        this.fileUrl = str3;
        this.forceUpdate = str4;
        this.description = str5;
        this.publicTime = str6;
    }
}

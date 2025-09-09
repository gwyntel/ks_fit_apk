package com.aliyun.player.source;

/* loaded from: classes3.dex */
public class VidSts extends VidSourceBase {
    private String mAccessKeyId;
    private String mAccessKeySecret;
    private String mRegion;
    private String mSecurityToken;
    private String mVid;

    public String getAccessKeyId() {
        return this.mAccessKeyId;
    }

    public String getAccessKeySecret() {
        return this.mAccessKeySecret;
    }

    public String getRegion() {
        return this.mRegion;
    }

    public String getSecurityToken() {
        return this.mSecurityToken;
    }

    public String getVid() {
        return this.mVid;
    }

    public void setAccessKeyId(String str) {
        this.mAccessKeyId = str;
    }

    public void setAccessKeySecret(String str) {
        this.mAccessKeySecret = str;
    }

    public void setQuality(String str, boolean z2) {
        this.mQuality = str;
        this.mForceQuality = z2;
    }

    public void setRegion(String str) {
        this.mRegion = str;
    }

    public void setSecurityToken(String str) {
        this.mSecurityToken = str;
    }

    public void setVid(String str) {
        this.mVid = str;
    }
}

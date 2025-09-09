package com.aliyun.alink.linksdk.securesigner;

/* loaded from: classes2.dex */
public class SecuritySourceContext {
    private static SecuritySourceContext instance = new SecuritySourceContext();
    private String appKey;
    private String appSecretKey;
    private ISecuritySource iSecuritySource;

    private SecuritySourceContext() {
    }

    public static SecuritySourceContext getInstance() {
        return instance;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public String getAppSecretKey() {
        return this.appSecretKey;
    }

    public ISecuritySource getISecuritySource() {
        return this.iSecuritySource;
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public void setAppSecretKey(String str) {
        this.appSecretKey = str;
    }

    public void setISecuritySource(ISecuritySource iSecuritySource) {
        this.iSecuritySource = iSecuritySource;
    }
}

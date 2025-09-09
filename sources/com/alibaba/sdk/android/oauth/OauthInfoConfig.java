package com.alibaba.sdk.android.oauth;

/* loaded from: classes2.dex */
public class OauthInfoConfig {
    public String accessTokenKey;
    public String appKeyFieldName;
    public String openIdKey;
    public String spareAccessTokenKey;

    public OauthInfoConfig(String str, String str2, String str3) {
        this(str, str2, str3, str3);
    }

    public OauthInfoConfig(String str, String str2, String str3, String str4) {
        this.openIdKey = str2;
        this.accessTokenKey = str3;
        this.spareAccessTokenKey = str4;
        this.appKeyFieldName = str;
    }
}

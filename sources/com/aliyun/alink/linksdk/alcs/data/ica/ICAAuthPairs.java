package com.aliyun.alink.linksdk.alcs.data.ica;

/* loaded from: classes2.dex */
public class ICAAuthPairs {
    public ICAAuthParams authParams;
    public ICAAuthServerParams authServerParams;

    public ICAAuthPairs(String str, String str2, String str3, String str4) {
        ICAAuthParams iCAAuthParams = new ICAAuthParams();
        this.authParams = iCAAuthParams;
        iCAAuthParams.accessKey = str;
        iCAAuthParams.accessToken = str2;
        ICAAuthServerParams iCAAuthServerParams = new ICAAuthServerParams();
        this.authServerParams = iCAAuthServerParams;
        iCAAuthServerParams.authCode = str3;
        iCAAuthServerParams.authSecret = str4;
    }
}

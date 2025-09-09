package com.aliyun.alink.linksdk.securesigner;

/* loaded from: classes2.dex */
public interface ISecuritySource {
    String getAppKey();

    String sign(String str, String str2);
}

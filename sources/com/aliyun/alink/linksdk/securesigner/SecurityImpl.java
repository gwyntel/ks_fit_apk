package com.aliyun.alink.linksdk.securesigner;

import com.aliyun.alink.linksdk.securesigner.util.Utils;

/* loaded from: classes2.dex */
public class SecurityImpl implements ISecuritySource {
    @Override // com.aliyun.alink.linksdk.securesigner.ISecuritySource
    public String getAppKey() {
        if (SecuritySourceContext.getInstance().getAppKey() != null) {
            return SecuritySourceContext.getInstance().getAppKey();
        }
        if (SecuritySourceContext.getInstance().getISecuritySource() == null || SecuritySourceContext.getInstance().getISecuritySource().getAppKey() == null) {
            throw new RuntimeException("can not get appKey");
        }
        return SecuritySourceContext.getInstance().getISecuritySource().getAppKey();
    }

    @Override // com.aliyun.alink.linksdk.securesigner.ISecuritySource
    public String sign(String str, String str2) {
        if (SecuritySourceContext.getInstance().getAppSecretKey() != null) {
            return Utils.MD5.equals(str2) ? Utils.getMD5String(str).toLowerCase() : "HmacSHA256".equals(str2) ? Utils.hmacSha256(SecuritySourceContext.getInstance().getAppSecretKey().getBytes(), str.getBytes()).toLowerCase() : Utils.hmacSha1(SecuritySourceContext.getInstance().getAppSecretKey(), str).toLowerCase();
        }
        if (SecuritySourceContext.getInstance().getISecuritySource() != null) {
            return SecuritySourceContext.getInstance().getISecuritySource().sign(str, str2).toLowerCase();
        }
        throw new RuntimeException("can not get appSecretKey or CustomSecuritySource");
    }
}

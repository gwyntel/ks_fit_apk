package com.aliyun.alink.linksdk.securesigner;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.securesigner.util.Utils;

/* loaded from: classes2.dex */
public class DefaultSecuritySourceImpl implements ISecuritySource {
    @Override // com.aliyun.alink.linksdk.securesigner.ISecuritySource
    public String getAppKey() {
        return SecuritySourceContext.getInstance().getAppKey();
    }

    @Override // com.aliyun.alink.linksdk.securesigner.ISecuritySource
    public String sign(String str, String str2) {
        return Utils.hexStr2Base64Str(((TextUtils.isEmpty(str2) || str2.equals("HmacSHA1")) ? Utils.hmacSha1(SecuritySourceContext.getInstance().getAppSecretKey(), str) : str2.equals(Utils.MD5) ? Utils.getMD5String(str) : null).toLowerCase());
    }
}

package com.aliyun.alink.linksdk.tmp.data.auth;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes2.dex */
public class ProvisionAuthData {
    public String accessKey;
    public String accessToken;
    public String authCode;
    public String authSecret;
    public String deviceName;
    public String productKey;

    public ProvisionAuthData(String str, String str2, AuthPairData authPairData) {
        this.productKey = str;
        this.deviceName = str2;
        this.accessKey = authPairData.accessKey;
        this.accessToken = authPairData.accessToken;
        this.authCode = authPairData.authCode;
        this.authSecret = authPairData.authSecret;
    }

    public String getId() {
        return TextHelper.combineStr(this.productKey, this.deviceName);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean zIsEmpty = TextUtils.isEmpty(this.productKey);
        String str = TmpConstant.GROUP_ROLE_UNKNOWN;
        sb.append(zIsEmpty ? TmpConstant.GROUP_ROLE_UNKNOWN : this.productKey);
        sb.append(TextUtils.isEmpty(this.deviceName) ? TmpConstant.GROUP_ROLE_UNKNOWN : this.deviceName);
        sb.append(TextUtils.isEmpty(this.accessKey) ? TmpConstant.GROUP_ROLE_UNKNOWN : this.accessKey);
        sb.append(TextUtils.isEmpty(this.accessToken) ? TmpConstant.GROUP_ROLE_UNKNOWN : this.accessToken);
        sb.append(TextUtils.isEmpty(this.authCode) ? TmpConstant.GROUP_ROLE_UNKNOWN : this.authCode);
        if (!TextUtils.isEmpty(this.authSecret)) {
            str = this.authSecret;
        }
        sb.append(str);
        return sb.toString();
    }

    public ProvisionAuthData() {
    }
}

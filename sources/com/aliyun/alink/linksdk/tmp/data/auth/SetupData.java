package com.aliyun.alink.linksdk.tmp.data.auth;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class SetupData {
    public String configType = TmpConstant.CONFIG_TYPE_PROVISION_RECEIVER;
    public List<ProvisionAuthData> configValue = new ArrayList();

    public String toString() {
        boolean zIsEmpty = TextUtils.isEmpty(this.configType);
        String string = TmpConstant.GROUP_ROLE_UNKNOWN;
        StringBuilder sb = new StringBuilder(zIsEmpty ? TmpConstant.GROUP_ROLE_UNKNOWN : this.configType);
        List<ProvisionAuthData> list = this.configValue;
        if (list != null) {
            string = list.toString();
        }
        sb.append(string);
        sb.append(super.toString());
        return sb.toString();
    }
}

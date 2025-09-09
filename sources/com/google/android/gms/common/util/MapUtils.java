package com.google.android.gms.common.util;

import androidx.annotation.NonNull;
import com.alipay.sdk.m.u.i;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.HashMap;

@KeepForSdk
/* loaded from: classes3.dex */
public class MapUtils {
    @KeepForSdk
    public static void writeStringMapToJson(@NonNull StringBuilder sb, @NonNull HashMap<String, String> map) {
        sb.append("{");
        boolean z2 = true;
        for (String str : map.keySet()) {
            if (!z2) {
                sb.append(",");
            }
            String str2 = map.get(str);
            sb.append("\"");
            sb.append(str);
            sb.append("\":");
            if (str2 == null) {
                sb.append(TmpConstant.GROUP_ROLE_UNKNOWN);
            } else {
                sb.append("\"");
                sb.append(str2);
                sb.append("\"");
            }
            z2 = false;
        }
        sb.append(i.f9804d);
    }
}

package com.alibaba.cloudapi.sdk.util;

import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/* loaded from: classes2.dex */
public class HttpCommonUtil {
    public static String buildParamString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null && map.size() > 0) {
            boolean z2 = true;
            for (String str : map.keySet()) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append("&");
                }
                try {
                    sb.append(str);
                    sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                    sb.append(URLEncoder.encode(map.get(str), SdkConstant.CLOUDAPI_ENCODING.displayName()));
                } catch (UnsupportedEncodingException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return sb.toString();
    }
}

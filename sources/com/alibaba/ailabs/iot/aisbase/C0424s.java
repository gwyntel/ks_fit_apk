package com.alibaba.ailabs.iot.aisbase;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* renamed from: com.alibaba.ailabs.iot.aisbase.s, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0424s {
    public static String a(@Nullable SparseArray<byte[]> sparseArray) {
        if (sparseArray == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (sparseArray.size() == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            sb.append(sparseArray.keyAt(i2));
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb.append(Arrays.toString(sparseArray.valueAt(i2)));
        }
        sb.append('}');
        return sb.toString();
    }

    public static <T> String a(@Nullable Map<T, byte[]> map) {
        if (map == null) {
            return TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        if (map.isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Iterator<Map.Entry<T, byte[]>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            T key = it.next().getKey();
            sb.append(key);
            sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
            sb.append(Arrays.toString(map.get(key)));
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}

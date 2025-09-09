package com.huawei.hms.opendevice;

import android.text.TextUtils;

/* loaded from: classes4.dex */
public class q {
    public static boolean a(String... strArr) {
        for (String str : strArr) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
        }
        return true;
    }
}

package com.alibaba.sdk.android.openaccount.ui.util;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public class StringUtils {
    public static boolean isEmail(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.contains("@");
    }
}

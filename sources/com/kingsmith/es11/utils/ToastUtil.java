package com.kingsmith.es11.utils;

import android.content.Context;
import android.widget.Toast;

/* loaded from: classes4.dex */
public class ToastUtil {
    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, 0).show();
    }
}

package com.aliyun.alink.business.devicecenter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.WifiProvisionUtConst;

/* loaded from: classes2.dex */
public class ProvisionSPUtils {

    /* renamed from: a, reason: collision with root package name */
    public static SharedPreferences f10651a;

    public static SharedPreferences a(Context context) {
        if (f10651a == null) {
            f10651a = context.getSharedPreferences(WifiProvisionUtConst.ARG_CONNECTION, 0);
        }
        return f10651a;
    }

    public static int getInt(Context context, String str, int i2) {
        return a(context).getInt(str, i2);
    }

    public static String getString(Context context, String str, String str2) {
        return a(context).getString(str, str2);
    }

    public static void putInt(Context context, String str, int i2) {
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putInt(str, i2);
        editorEdit.apply();
    }

    public static void putString(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
    }
}

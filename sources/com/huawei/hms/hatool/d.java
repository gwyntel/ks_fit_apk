package com.huawei.hms.hatool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import java.util.Map;

@SuppressLint({"ApplySharedPref"})
/* loaded from: classes4.dex */
public class d {
    public static long a(Context context, String str, String str2, long j2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            v.f("hmsSdk", "context is null or spName empty or spkey is empty");
            return j2;
        }
        SharedPreferences sharedPreferencesB = b(context, str);
        return sharedPreferencesB != null ? sharedPreferencesB.getLong(str2, j2) : j2;
    }

    private static SharedPreferences b(Context context, String str) {
        return context.getSharedPreferences(c(context, str), 0);
    }

    public static String c(Context context, String str) {
        String packageName = context.getPackageName();
        String strN = a1.n("_hms_config_tag", "oper");
        if (TextUtils.isEmpty(strN)) {
            return "hms_" + str + OpenAccountUIConstants.UNDER_LINE + packageName;
        }
        return "hms_" + str + OpenAccountUIConstants.UNDER_LINE + packageName + OpenAccountUIConstants.UNDER_LINE + strN;
    }

    public static String a(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            v.f("hmsSdk", "context is null or spName empty or spkey is empty");
            return str3;
        }
        SharedPreferences sharedPreferencesB = b(context, str);
        return sharedPreferencesB != null ? sharedPreferencesB.getString(str2, str3) : str3;
    }

    public static void b(Context context, String str, String str2, long j2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            v.f("hmsSdk", "context is null or spName empty or spkey is empty");
            return;
        }
        SharedPreferences sharedPreferencesB = b(context, str);
        if (sharedPreferencesB != null) {
            SharedPreferences.Editor editorEdit = sharedPreferencesB.edit();
            editorEdit.putLong(str2, j2);
            editorEdit.commit();
        }
    }

    public static Map<String, ?> a(Context context, String str) {
        return b(context, str).getAll();
    }

    public static void b(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            v.e("hmsSdk", "context is null or spName empty or spkey is empty");
            return;
        }
        SharedPreferences sharedPreferencesB = b(context, str);
        if (sharedPreferencesB != null) {
            SharedPreferences.Editor editorEdit = sharedPreferencesB.edit();
            editorEdit.putString(str2, str3);
            editorEdit.commit();
        }
    }

    public static void a(Context context, String str, String... strArr) {
        String str2;
        if (context == null || TextUtils.isEmpty(str)) {
            str2 = "clearData(): parameter error.context,spname";
        } else {
            if (strArr != null) {
                SharedPreferences sharedPreferencesB = b(context, str);
                if (sharedPreferencesB != null) {
                    SharedPreferences.Editor editorEdit = sharedPreferencesB.edit();
                    if (strArr.length == 0) {
                        editorEdit.clear();
                        editorEdit.commit();
                        return;
                    }
                    for (String str3 : strArr) {
                        if (sharedPreferencesB.contains(str3)) {
                            editorEdit.remove(str3);
                            editorEdit.commit();
                        }
                    }
                    return;
                }
                return;
            }
            str2 = "clearData(): No data need to be deleted,keys is null";
        }
        v.f("hmsSdk", str2);
    }
}

package com.huawei.hms.hatool;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.Pair;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class x0 extends o {
    public static String c() {
        String str;
        String str2;
        String str3 = "";
        try {
            str2 = (String) Class.forName("com.huawei.android.os.BuildEx").getMethod("getUDID", null).invoke(null, null);
        } catch (AndroidRuntimeException unused) {
        } catch (ClassNotFoundException unused2) {
        } catch (IllegalAccessException unused3) {
        } catch (IllegalArgumentException unused4) {
        } catch (NoSuchMethodException unused5) {
        } catch (InvocationTargetException unused6) {
        }
        try {
            v.c("hmsSdk", "getUDID success");
            return str2;
        } catch (AndroidRuntimeException unused7) {
            str3 = str2;
            str = "getUDID getudid failed, RuntimeException is AndroidRuntimeException";
            v.f("hmsSdk", str);
            return str3;
        } catch (ClassNotFoundException unused8) {
            str3 = str2;
            str = "getUDID method invoke failed";
            v.f("hmsSdk", str);
            return str3;
        } catch (IllegalAccessException unused9) {
            str3 = str2;
            str = "getUDID method invoke failed : Illegal AccessException";
            v.f("hmsSdk", str);
            return str3;
        } catch (IllegalArgumentException unused10) {
            str3 = str2;
            str = "getUDID method invoke failed : Illegal ArgumentException";
            v.f("hmsSdk", str);
            return str3;
        } catch (NoSuchMethodException unused11) {
            str3 = str2;
            str = "getUDID method invoke failed : NoSuchMethodException";
            v.f("hmsSdk", str);
            return str3;
        } catch (InvocationTargetException unused12) {
            str3 = str2;
            str = "getUDID method invoke failed : InvocationTargetException";
            v.f("hmsSdk", str);
            return str3;
        }
    }

    public static Pair<String, String> e(Context context) {
        if (c0.a(context, "android.permission.READ_PHONE_STATE")) {
            v.f("hmsSdk", "getMccAndMnc() Pair value is empty");
            return new Pair<>("", "");
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null && telephonyManager.getSimState() == 5) {
            String networkOperator = telephonyManager.getNetworkOperator();
            return (TextUtils.isEmpty(networkOperator) || TextUtils.equals(networkOperator, TmpConstant.GROUP_ROLE_UNKNOWN)) ? new Pair<>("", "") : networkOperator.length() > 3 ? new Pair<>(networkOperator.substring(0, 3), networkOperator.substring(3)) : new Pair<>("", "");
        }
        return new Pair<>("", "");
    }
}

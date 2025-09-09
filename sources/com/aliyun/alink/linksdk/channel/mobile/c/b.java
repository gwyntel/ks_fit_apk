package com.aliyun.alink.linksdk.channel.mobile.c;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;

/* loaded from: classes2.dex */
public class b {
    public static boolean a(Context context, String str, String str2) {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("SecurityGuardDataStoreUtil", "putString, key = " + str + ", value=" + str2);
        if (context != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
                if (securityGuardManager != null && (dynamicDataStoreComp = securityGuardManager.getDynamicDataStoreComp()) != null) {
                    return dynamicDataStoreComp.putStringDDpEx(str, str2, 0);
                }
            } catch (Exception e2) {
                com.aliyun.alink.linksdk.channel.mobile.a.a.a("SecurityGuardDataStoreUtil", "putString(),error, " + e2.toString());
            }
        }
        return false;
    }

    public static String a(Context context, String str) {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("SecurityGuardDataStoreUtil", "getString, key = " + str);
        if (context != null && !TextUtils.isEmpty(str)) {
            try {
                SecurityGuardManager securityGuardManager = SecurityGuardManager.getInstance(context);
                if (securityGuardManager != null && (dynamicDataStoreComp = securityGuardManager.getDynamicDataStoreComp()) != null) {
                    return dynamicDataStoreComp.getStringDDpEx(str, 0);
                }
            } catch (Exception e2) {
                com.aliyun.alink.linksdk.channel.mobile.a.a.a("SecurityGuardDataStoreUtil", "getString(),error, " + e2.toString());
            }
        }
        return null;
    }
}

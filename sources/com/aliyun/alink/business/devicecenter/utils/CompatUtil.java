package com.aliyun.alink.business.devicecenter.utils;

import com.aliyun.alink.business.devicecenter.log.ALog;

/* loaded from: classes2.dex */
public class CompatUtil {
    public static boolean isAlinkPhoneApConfigStrategyFromOldHotspotFlow() {
        try {
            return ((Boolean) ReflectUtils.callStaticMethod("com.aliyun.alink.business.devicecenter.provision.other.phoneap.AlinkPhoneApConfigStrategy", "isFromOldHotspotFlow", new Object[0])).booleanValue();
        } catch (Exception e2) {
            ALog.w("CompatUtil", "call isFromOldHotspotFlow exception: " + e2);
            e2.printStackTrace();
            return false;
        }
    }
}

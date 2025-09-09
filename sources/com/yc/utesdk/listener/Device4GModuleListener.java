package com.yc.utesdk.listener;

import com.yc.utesdk.bean.Device4GModuleInfo;

/* loaded from: classes4.dex */
public interface Device4GModuleListener {
    public static final int SET_4G_REPORT_INTERVAL = 1;

    void onDevice4GModuleIMEI(boolean z2, Device4GModuleInfo device4GModuleInfo);

    void onDevice4GModuleStatus(boolean z2, int i2);
}

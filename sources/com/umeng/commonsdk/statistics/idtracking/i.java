package com.umeng.commonsdk.statistics.idtracking;

import android.annotation.TargetApi;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes4.dex */
public class i extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22409a = "serial";

    public i() {
        super(f22409a);
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    @TargetApi(9)
    public String f() {
        return DeviceConfig.getSerial();
    }
}

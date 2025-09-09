package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes4.dex */
public class d extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22386a = "idmd5";

    /* renamed from: b, reason: collision with root package name */
    private Context f22387b;

    public d(Context context) {
        super("idmd5");
        this.f22387b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return DeviceConfig.getDeviceIdUmengMD5(this.f22387b);
    }
}

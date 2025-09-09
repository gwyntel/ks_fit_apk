package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes4.dex */
public class b extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22380a = "android_id";

    /* renamed from: b, reason: collision with root package name */
    private Context f22381b;

    public b(Context context) {
        super(f22380a);
        this.f22381b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return DeviceConfig.getAndroidId(this.f22381b);
    }
}

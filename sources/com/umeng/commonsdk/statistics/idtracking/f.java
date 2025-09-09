package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes4.dex */
public class f extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22400a = "imei";

    /* renamed from: b, reason: collision with root package name */
    private Context f22401b;

    public f(Context context) {
        super("imei");
        this.f22401b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return DeviceConfig.getImeiNew(this.f22401b);
    }
}

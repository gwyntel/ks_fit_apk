package com.huawei.hms.framework.network.grs.g.j;

import android.os.SystemClock;
import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private final Future<com.huawei.hms.framework.network.grs.g.d> f16287a;

    /* renamed from: b, reason: collision with root package name */
    private final long f16288b = SystemClock.elapsedRealtime();

    public b(Future<com.huawei.hms.framework.network.grs.g.d> future) {
        this.f16287a = future;
    }

    public Future<com.huawei.hms.framework.network.grs.g.d> a() {
        return this.f16287a;
    }

    public boolean b() {
        return SystemClock.elapsedRealtime() - this.f16288b <= 300000;
    }
}

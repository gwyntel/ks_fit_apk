package com.hihonor.push.sdk;

import android.os.Looper;
import android.util.Log;
import com.hihonor.push.framework.aidl.IPushInvoke;
import com.hihonor.push.sdk.b0;
import com.hihonor.push.sdk.internal.HonorPushErrorEnum;
import com.hihonor.push.sdk.z;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class d0 implements b0 {

    /* renamed from: a, reason: collision with root package name */
    public final AtomicInteger f15473a = new AtomicInteger(1);

    /* renamed from: b, reason: collision with root package name */
    public volatile IPushInvoke f15474b;

    /* renamed from: c, reason: collision with root package name */
    public final b0.a f15475c;

    /* renamed from: d, reason: collision with root package name */
    public f0 f15476d;

    public d0(b0.a aVar) {
        this.f15475c = aVar;
    }

    public boolean a() {
        return this.f15473a.get() == 3 || this.f15473a.get() == 4;
    }

    public final void a(int i2) {
        Log.i("PushConnectionClient", "notifyFailed result: " + i2);
        b0.a aVar = this.f15475c;
        if (aVar != null) {
            z.a aVar2 = (z.a) aVar;
            if (Looper.myLooper() == z.this.f15570a.getLooper()) {
                aVar2.a(HonorPushErrorEnum.fromCode(i2));
            } else {
                z.this.f15570a.post(new y(aVar2, i2));
            }
        }
    }
}

package com.alibaba.ailabs.iot.aisbase;

import androidx.annotation.RequiresPermission;
import com.alibaba.ailabs.iot.aisbase.C0418p;

/* renamed from: com.alibaba.ailabs.iot.aisbase.n, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0414n implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f8440a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0416o f8441b;

    public RunnableC0414n(C0416o c0416o, int i2) {
        this.f8441b = c0416o;
        this.f8440a = i2;
    }

    @Override // java.lang.Runnable
    @RequiresPermission(allOf = {"android.permission.BLUETOOTH_ADMIN", "android.permission.BLUETOOTH"})
    public void run() {
        if (!this.f8441b.f8445b.f1664g.getUseHardwareCallbackTypesIfSupported() || this.f8441b.f8445b.f1664g.getCallbackType() == 1) {
            this.f8441b.f8445b.a(this.f8440a);
            return;
        }
        this.f8441b.f8445b.f1664g.a();
        C0418p.a aVar = this.f8441b.f8445b;
        C0418p.this.stopScan(aVar.f1665h);
        C0418p.a aVar2 = this.f8441b.f8445b;
        C0418p.this.a(aVar2.f1663f, aVar2.f1664g, aVar2.f1665h, aVar2.f1666i);
    }
}

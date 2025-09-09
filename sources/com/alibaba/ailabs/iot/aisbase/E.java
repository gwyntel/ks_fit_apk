package com.alibaba.ailabs.iot.aisbase;

/* loaded from: classes2.dex */
public class E implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ F f8276a;

    public E(F f2) {
        this.f8276a = f2;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f8276a.f8282e.mGetAuthRandomTimeoutTask = null;
        F f2 = this.f8276a;
        f2.f8282e.startAuth(f2.f8278a, f2.f8280c, f2.f8281d, f2.f8279b);
    }
}

package com.hihonor.push.sdk;

import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ v f15477a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ l f15478b;

    public e(l lVar, v vVar) {
        this.f15478b = lVar;
        this.f15477a = vVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f15478b.f15513b) {
            return;
        }
        this.f15478b.f15513b = true;
        this.f15478b.getClass();
        this.f15478b.f15512a = new WeakReference<>(this.f15477a.f15553a);
        this.f15478b.f15514c = this.f15477a.f15554b;
        this.f15478b.f15515d = new s(this.f15477a.f15553a);
        if (this.f15478b.f15514c) {
            l lVar = this.f15478b;
            lVar.a(new f(lVar, null, true), (HonorPushCallback<?>) null);
        }
    }
}

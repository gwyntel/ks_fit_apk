package com.taobao.accs.utl;

import com.taobao.accs.base.AccsDataListener;

/* loaded from: classes4.dex */
class g implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AccsDataListener f20380a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ boolean f20381b;

    g(AccsDataListener accsDataListener, boolean z2) {
        this.f20380a = accsDataListener;
        this.f20381b = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f20380a.onAntiBrush(this.f20381b, null);
    }
}

package com.taobao.accs.utl;

import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.base.TaoBaseService;

/* loaded from: classes4.dex */
class h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AccsDataListener f20382a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f20383b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ boolean f20384c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ boolean f20385d;

    h(AccsDataListener accsDataListener, String str, boolean z2, boolean z3) {
        this.f20382a = accsDataListener;
        this.f20383b = str;
        this.f20384c = z2;
        this.f20385d = z3;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f20382a.onConnected(new TaoBaseService.ConnectInfo(this.f20383b, this.f20384c, this.f20385d));
    }
}

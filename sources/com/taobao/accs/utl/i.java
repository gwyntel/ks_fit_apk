package com.taobao.accs.utl;

import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.base.TaoBaseService;

/* loaded from: classes4.dex */
class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AccsDataListener f20386a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f20387b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ boolean f20388c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ boolean f20389d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ int f20390e;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ String f20391f;

    i(AccsDataListener accsDataListener, String str, boolean z2, boolean z3, int i2, String str2) {
        this.f20386a = accsDataListener;
        this.f20387b = str;
        this.f20388c = z2;
        this.f20389d = z3;
        this.f20390e = i2;
        this.f20391f = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f20386a.onDisconnected(new TaoBaseService.ConnectInfo(this.f20387b, this.f20388c, this.f20389d, this.f20390e, this.f20391f));
    }
}

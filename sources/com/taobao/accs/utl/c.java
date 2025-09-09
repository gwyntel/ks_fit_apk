package com.taobao.accs.utl;

import android.content.Intent;
import com.taobao.accs.base.AccsDataListener;

/* loaded from: classes4.dex */
class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AccsDataListener f20356a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f20357b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ int f20358c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ Intent f20359d;

    c(AccsDataListener accsDataListener, String str, int i2, Intent intent) {
        this.f20356a = accsDataListener;
        this.f20357b = str;
        this.f20358c = i2;
        this.f20359d = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f20356a.onUnbind(this.f20357b, this.f20358c, a.c(this.f20359d));
    }
}

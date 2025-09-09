package com.taobao.accs.utl;

import android.content.Intent;
import com.taobao.accs.base.AccsDataListener;

/* loaded from: classes4.dex */
class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AccsDataListener f20352a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f20353b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ int f20354c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ Intent f20355d;

    b(AccsDataListener accsDataListener, String str, int i2, Intent intent) {
        this.f20352a = accsDataListener;
        this.f20353b = str;
        this.f20354c = i2;
        this.f20355d = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f20352a.onBind(this.f20353b, this.f20354c, a.c(this.f20355d));
    }
}

package com.taobao.accs.net;

import android.text.TextUtils;
import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class t implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ k f20260a;

    t(k kVar) {
        this.f20260a = kVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            k kVar = this.f20260a;
            if (kVar.f20194d == null || TextUtils.isEmpty(kVar.i())) {
                return;
            }
            ALog.i(this.f20260a.d(), "mTryStartServiceRunable bindapp", new Object[0]);
            this.f20260a.k();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}

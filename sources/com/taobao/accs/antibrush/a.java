package com.taobao.accs.antibrush;

import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ AntiBrush f20054a;

    a(AntiBrush antiBrush) {
        this.f20054a = antiBrush;
    }

    @Override // java.lang.Runnable
    public void run() {
        ALog.e("AntiBrush", "anti bursh timeout", new Object[0]);
        AntiBrush.onResult(this.f20054a.mContext, false);
    }
}

package com.taobao.accs.client;

import com.taobao.accs.utl.UtilityImpl;

/* loaded from: classes4.dex */
class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ GlobalClientInfo f20094a;

    c(GlobalClientInfo globalClientInfo) {
        this.f20094a = globalClientInfo;
    }

    @Override // java.lang.Runnable
    public void run() {
        GlobalClientInfo.f20067c = UtilityImpl.m(GlobalClientInfo.f20065a);
    }
}

package com.taobao.accs.internal;

import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.l;

/* loaded from: classes4.dex */
class a implements l.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ACCSManagerImpl f20181a;

    a(ACCSManagerImpl aCCSManagerImpl) {
        this.f20181a = aCCSManagerImpl;
    }

    @Override // com.taobao.accs.utl.l.b
    public void a() {
        try {
            ALog.e(ACCSManagerImpl.f20170c, "onForeState", new Object[0]);
            if (AccsClientConfig.getConfigByTag(this.f20181a.f20172b).isForePingEnable()) {
                ACCSManagerImpl aCCSManagerImpl = this.f20181a;
                aCCSManagerImpl.a(aCCSManagerImpl.f20173d);
            }
        } catch (Exception e2) {
            ALog.e(ACCSManagerImpl.f20170c, "onForeState error, Error:", e2, new Object[0]);
        }
    }

    @Override // com.taobao.accs.utl.l.b
    public void b() {
        ALog.e(ACCSManagerImpl.f20170c, "onBackState", new Object[0]);
    }
}

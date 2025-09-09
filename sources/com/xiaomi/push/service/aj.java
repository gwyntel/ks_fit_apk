package com.xiaomi.push.service;

import android.text.TextUtils;
import com.xiaomi.push.in;
import com.xiaomi.push.ix;
import com.xiaomi.push.jm;
import com.xiaomi.push.jx;
import com.xiaomi.push.service.by;
import java.util.HashMap;

/* loaded from: classes4.dex */
class aj extends by.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24457a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ u f999a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    aj(String str, long j2, XMPushService xMPushService, u uVar) {
        super(str, j2);
        this.f24457a = xMPushService;
        this.f999a = uVar;
    }

    @Override // com.xiaomi.push.service.by.a
    void a(by byVar) {
        com.xiaomi.push.ax axVarA = com.xiaomi.push.ax.a(this.f24457a);
        String strA = byVar.a("MSAID", "msaid");
        String strMo179a = axVarA.mo179a();
        if (TextUtils.isEmpty(strMo179a) || TextUtils.equals(strA, strMo179a)) {
            return;
        }
        byVar.a("MSAID", "msaid", strMo179a);
        jm jmVar = new jm();
        jmVar.b(this.f999a.f24626d);
        jmVar.c(ix.ClientInfoUpdate.f620a);
        jmVar.a(bc.a());
        jmVar.a(new HashMap());
        axVarA.a(jmVar.m609a());
        byte[] bArrA = jx.a(ai.a(this.f24457a.getPackageName(), this.f999a.f24626d, jmVar, in.Notification));
        XMPushService xMPushService = this.f24457a;
        xMPushService.a(xMPushService.getPackageName(), bArrA, true);
    }
}

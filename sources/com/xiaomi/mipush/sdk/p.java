package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.ah;
import com.xiaomi.push.in;
import com.xiaomi.push.ja;
import com.xiaomi.push.jm;
import com.xiaomi.push.service.bc;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
class p extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23419a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ jm f153a;

    p(jm jmVar, Context context) {
        this.f153a = jmVar;
        this.f23419a = context;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return AgooConstants.REPORT_ENCRYPT_FAIL;
    }

    @Override // java.lang.Runnable
    public void run() {
        jm jmVar = this.f153a;
        if (jmVar != null) {
            jmVar.a(bc.a());
            ao.a(this.f23419a.getApplicationContext()).a((ao) this.f153a, in.Notification, true, (ja) null, true);
        }
    }
}

package com.xiaomi.push.service;

import com.xiaomi.push.ah;
import com.xiaomi.push.in;
import com.xiaomi.push.jm;
import com.xiaomi.push.jx;
import java.lang.ref.WeakReference;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public class b extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    private jm f24490a;

    /* renamed from: a, reason: collision with other field name */
    private WeakReference<XMPushService> f1027a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f1028a;

    public b(jm jmVar, WeakReference<XMPushService> weakReference, boolean z2) {
        this.f24490a = jmVar;
        this.f1027a = weakReference;
        this.f1028a = z2;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return AgooConstants.REPORT_ENCRYPT_FAIL;
    }

    @Override // java.lang.Runnable
    public void run() {
        XMPushService xMPushService;
        WeakReference<XMPushService> weakReference = this.f1027a;
        if (weakReference == null || this.f24490a == null || (xMPushService = weakReference.get()) == null) {
            return;
        }
        this.f24490a.a(bc.a());
        this.f24490a.a(false);
        com.xiaomi.channel.commonutils.logger.b.c("MoleInfo aw_ping : send aw_Ping msg " + this.f24490a.m608a());
        try {
            String strC = this.f24490a.c();
            xMPushService.a(strC, jx.a(ai.a(strC, this.f24490a.b(), this.f24490a, in.Notification)), this.f1028a);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("MoleInfo aw_ping : send help app ping error" + e2.toString());
        }
    }
}

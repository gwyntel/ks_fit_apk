package com.xiaomi.push.service;

import com.xiaomi.push.service.by;
import java.util.Iterator;

/* loaded from: classes4.dex */
class bz implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ by f24568a;

    bz(by byVar) {
        this.f24568a = byVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Iterator it = this.f24568a.f1065a.values().iterator();
            while (it.hasNext()) {
                ((by.a) it.next()).run();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Sync job exception :" + e2.getMessage());
        }
        this.f24568a.f1066a = false;
    }
}

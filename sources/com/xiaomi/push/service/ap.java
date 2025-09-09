package com.xiaomi.push.service;

import com.xiaomi.push.go;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
class ap implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ List f24466a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f1013a;

    ap(List list, boolean z2) {
        this.f24466a = list;
        this.f1013a = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean zB = ao.b("www.baidu.com:80");
        Iterator it = this.f24466a.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            zB = zB || ao.b((String) it.next());
            if (zB && !this.f1013a) {
                break;
            }
        }
        go.a(zB ? 1 : 2);
    }
}

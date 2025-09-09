package com.taobao.accs.net;

import com.taobao.accs.data.Message;
import com.taobao.accs.utl.BaseMonitor;
import java.util.Iterator;

/* loaded from: classes4.dex */
class s implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f20256a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ boolean f20257b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ int f20258c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ k f20259d;

    s(k kVar, int i2, boolean z2, int i3) {
        this.f20259d = kVar;
        this.f20256a = i2;
        this.f20257b = z2;
        this.f20258c = i3;
    }

    @Override // java.lang.Runnable
    public void run() {
        Message.Id next;
        Message messageB;
        int i2 = this.f20256a;
        if (i2 > 0) {
            Message.Id id = new Message.Id(i2, "");
            Iterator<Message.Id> it = this.f20259d.f20195e.f().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (next.equals(id)) {
                        break;
                    }
                }
            }
            if (next != null && (messageB = this.f20259d.f20195e.b(next.getDataId())) != null) {
                if (this.f20257b) {
                    if (!this.f20259d.a(messageB, 2000)) {
                        this.f20259d.f20195e.a(messageB, this.f20258c);
                    }
                    if (messageB.getNetPermanceMonitor() != null) {
                        com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_RESEND, "total_tnet", 0.0d);
                    }
                } else {
                    this.f20259d.f20195e.a(messageB, this.f20258c);
                }
            }
        }
        int i3 = this.f20256a;
        if (i3 >= 0 || !this.f20257b) {
            return;
        }
        this.f20259d.b(i3);
    }
}

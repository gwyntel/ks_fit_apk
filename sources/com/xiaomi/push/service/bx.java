package com.xiaomi.push.service;

import android.util.Base64;
import com.xiaomi.push.C0472r;
import com.xiaomi.push.ak;
import com.xiaomi.push.dh;
import com.xiaomi.push.ew;
import com.xiaomi.push.service.bw;
import java.util.List;

/* loaded from: classes4.dex */
class bx extends ak.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bw f24565a;

    /* renamed from: a, reason: collision with other field name */
    boolean f1061a = false;

    bx(bw bwVar) {
        this.f24565a = bwVar;
    }

    @Override // com.xiaomi.push.ak.b
    public void b() {
        try {
            ew.a aVarA = ew.a.a(Base64.decode(dh.a(C0472r.m684a(), "https://resolver.msg.xiaomi.net/psc/?t=a", (List<com.xiaomi.push.bf>) null), 10));
            if (aVarA != null) {
                this.f24565a.f1059a = aVarA;
                this.f1061a = true;
                this.f24565a.e();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("fetch config failure: " + e2.getMessage());
        }
    }

    @Override // com.xiaomi.push.ak.b
    /* renamed from: c */
    public void mo308c() {
        bw.a[] aVarArr;
        this.f24565a.f1058a = null;
        if (this.f1061a) {
            synchronized (this.f24565a) {
                aVarArr = (bw.a[]) this.f24565a.f1060a.toArray(new bw.a[this.f24565a.f1060a.size()]);
            }
            for (bw.a aVar : aVarArr) {
                aVar.a(this.f24565a.f1059a);
            }
        }
    }
}

package com.xiaomi.push;

import com.xiaomi.push.gk;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.bf;

/* loaded from: classes4.dex */
class gh implements bf.b.a {

    /* renamed from: a, reason: collision with root package name */
    private int f23828a;

    /* renamed from: a, reason: collision with other field name */
    private hb f466a;

    /* renamed from: a, reason: collision with other field name */
    private XMPushService f467a;

    /* renamed from: a, reason: collision with other field name */
    private bf.b f468a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f470a = false;

    /* renamed from: a, reason: collision with other field name */
    private bf.c f469a = bf.c.binding;

    gh(XMPushService xMPushService, bf.b bVar) {
        this.f467a = xMPushService;
        this.f468a = bVar;
    }

    private void b() {
        this.f468a.b(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        b();
        if (this.f470a && this.f23828a != 11) {
            gf gfVarM438a = gm.m436a().m438a();
            int i2 = gj.f23830a[this.f469a.ordinal()];
            if (i2 == 1) {
                int i3 = this.f23828a;
                if (i3 == 17) {
                    gfVarM438a.f452a = ge.BIND_TCP_READ_TIMEOUT.a();
                } else if (i3 == 21) {
                    gfVarM438a.f452a = ge.BIND_TIMEOUT.a();
                } else {
                    try {
                        gk.a aVarC = gk.c(gm.a().a());
                        gfVarM438a.f452a = aVarC.f23831a.a();
                        gfVarM438a.c(aVarC.f471a);
                    } catch (NullPointerException unused) {
                        gfVarM438a = null;
                    }
                }
            } else if (i2 == 3) {
                gfVarM438a.f452a = ge.BIND_SUCCESS.a();
            }
            if (gfVarM438a != null) {
                gfVarM438a.b(this.f466a.mo468a());
                gfVarM438a.d(this.f468a.f1043b);
                gfVarM438a.f455b = 1;
                try {
                    gfVarM438a.a((byte) Integer.parseInt(this.f468a.f24503g));
                } catch (NumberFormatException unused2) {
                }
                gm.m436a().a(gfVarM438a);
            }
        }
    }

    void a() {
        this.f468a.a(this);
        this.f466a = this.f467a.m708a();
    }

    @Override // com.xiaomi.push.service.bf.b.a
    public void a(bf.c cVar, bf.c cVar2, int i2) {
        if (!this.f470a && cVar == bf.c.binding) {
            this.f469a = cVar2;
            this.f23828a = i2;
            this.f470a = true;
        }
        this.f467a.a(new gi(this, 4));
    }
}

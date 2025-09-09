package com.xiaomi.push;

import com.xiaomi.push.ex;
import com.xiaomi.push.hb;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/* loaded from: classes4.dex */
public class gz implements hn {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f23856a = false;

    /* renamed from: a, reason: collision with other field name */
    private hb f510a;

    /* renamed from: a, reason: collision with other field name */
    private SimpleDateFormat f513a = new SimpleDateFormat("hh:mm:ss aaa");

    /* renamed from: a, reason: collision with other field name */
    private a f509a = null;

    /* renamed from: b, reason: collision with root package name */
    private a f23857b = null;

    /* renamed from: a, reason: collision with other field name */
    private he f511a = null;

    /* renamed from: a, reason: collision with other field name */
    private final String f512a = "[Slim] ";

    class a implements hg, ho {

        /* renamed from: a, reason: collision with other field name */
        String f514a;

        /* renamed from: a, reason: collision with other field name */
        private boolean f515a;

        a(boolean z2) {
            this.f515a = z2;
            this.f514a = z2 ? " RCV " : " Sent ";
        }

        @Override // com.xiaomi.push.ho
        /* renamed from: a */
        public boolean mo281a(hs hsVar) {
            return true;
        }

        @Override // com.xiaomi.push.hg
        public void a(hs hsVar) {
            if (gz.f23856a) {
                com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + gz.this.f513a.format(new Date()) + this.f514a + " PKT " + hsVar.mo485a());
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + gz.this.f513a.format(new Date()) + this.f514a + " PKT [" + hsVar.k() + "," + hsVar.j() + "]");
        }

        @Override // com.xiaomi.push.hg
        public void a(gq gqVar) throws NumberFormatException {
            if (gz.f23856a) {
                com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + gz.this.f513a.format(new Date()) + this.f514a + gqVar.toString());
            } else {
                com.xiaomi.channel.commonutils.logger.b.c("[Slim] " + gz.this.f513a.format(new Date()) + this.f514a + " Blob [" + gqVar.m444a() + "," + gqVar.a() + "," + com.xiaomi.push.service.bc.a(gqVar.e()) + "]");
            }
            if (gqVar == null || gqVar.a() != 99999) {
                return;
            }
            String strM444a = gqVar.m444a();
            gq gqVar2 = null;
            if (!this.f515a) {
                if ("BIND".equals(strM444a)) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("build binded result for loopback.");
                    ex.d dVar = new ex.d();
                    dVar.a(true);
                    dVar.c("login success.");
                    dVar.b("success");
                    dVar.a("success");
                    gq gqVar3 = new gq();
                    gqVar3.a(dVar.m303a(), (String) null);
                    gqVar3.a((short) 2);
                    gqVar3.a(99999);
                    gqVar3.a("BIND", (String) null);
                    gqVar3.a(gqVar.e());
                    gqVar3.b((String) null);
                    gqVar3.c(gqVar.g());
                    gqVar2 = gqVar3;
                } else if (!"UBND".equals(strM444a) && "SECMSG".equals(strM444a)) {
                    gq gqVar4 = new gq();
                    gqVar4.a(99999);
                    gqVar4.a("SECMSG", (String) null);
                    gqVar4.c(gqVar.g());
                    gqVar4.a(gqVar.e());
                    gqVar4.a(gqVar.m446a());
                    gqVar4.b(gqVar.f());
                    gqVar4.a(gqVar.m449a(com.xiaomi.push.service.bf.a().a(String.valueOf(99999), gqVar.g()).f24504h), (String) null);
                    gqVar2 = gqVar4;
                }
            }
            if (gqVar2 != null) {
                for (Map.Entry<hg, hb.a> entry : gz.this.f510a.m469a().entrySet()) {
                    if (gz.this.f509a != entry.getKey()) {
                        entry.getValue().a(gqVar2);
                    }
                }
            }
        }
    }

    public gz(hb hbVar) {
        this.f510a = hbVar;
        a();
    }

    private void a() {
        this.f509a = new a(true);
        this.f23857b = new a(false);
        hb hbVar = this.f510a;
        a aVar = this.f509a;
        hbVar.a(aVar, aVar);
        hb hbVar2 = this.f510a;
        a aVar2 = this.f23857b;
        hbVar2.b(aVar2, aVar2);
        this.f511a = new ha(this);
    }
}

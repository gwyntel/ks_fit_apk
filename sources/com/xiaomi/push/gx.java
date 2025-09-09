package com.xiaomi.push;

import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.push.ex;
import com.xiaomi.push.hb;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.bf;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class gx extends hi {

    /* renamed from: a, reason: collision with root package name */
    private gs f23854a;

    /* renamed from: a, reason: collision with other field name */
    private gt f506a;

    /* renamed from: a, reason: collision with other field name */
    private Thread f507a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f508a;

    public gx(XMPushService xMPushService, hc hcVar) {
        super(xMPushService, hcVar);
    }

    private void h() throws hm {
        try {
            this.f23854a = new gs(((hi) this).f536a.getInputStream(), this);
            this.f506a = new gt(((hi) this).f536a.getOutputStream(), this);
            gy gyVar = new gy(this, "Blob Reader (" + ((hb) this).f23862b + ")");
            this.f507a = gyVar;
            gyVar.start();
        } catch (Exception e2) {
            throw new hm("Error to init reader and writer", e2);
        }
    }

    @Override // com.xiaomi.push.hb
    /* renamed from: a, reason: collision with other method in class */
    public boolean mo461a() {
        return true;
    }

    void b(hs hsVar) {
        if (hsVar == null) {
            return;
        }
        Iterator<hb.a> it = ((hb) this).f525a.values().iterator();
        while (it.hasNext()) {
            it.next().a(hsVar);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    synchronized byte[] m462a() {
        try {
            if (this.f508a == null && !TextUtils.isEmpty(((hb) this).f522a)) {
                String strM767a = com.xiaomi.push.service.bw.m767a();
                StringBuilder sb = new StringBuilder();
                String str = ((hb) this).f522a;
                sb.append(str.substring(str.length() / 2));
                sb.append(strM767a.substring(strM767a.length() / 2));
                this.f508a = com.xiaomi.push.service.bo.a(((hb) this).f522a.getBytes(), sb.toString().getBytes());
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f508a;
    }

    @Override // com.xiaomi.push.hb
    public void b(gq gqVar) throws hm {
        gt gtVar = this.f506a;
        if (gtVar != null) {
            try {
                int iA = gtVar.a(gqVar);
                ((hb) this).f23864d = SystemClock.elapsedRealtime();
                String strF = gqVar.f();
                if (!TextUtils.isEmpty(strF)) {
                    ig.a(((hb) this).f521a, strF, iA, false, true, System.currentTimeMillis());
                }
                Iterator<hb.a> it = ((hb) this).f528b.values().iterator();
                while (it.hasNext()) {
                    it.next().a(gqVar);
                }
                return;
            } catch (Exception e2) {
                throw new hm(e2);
            }
        }
        throw new hm("the writer is null.");
    }

    private gq a(boolean z2) {
        gw gwVar = new gw();
        if (z2) {
            gwVar.a("1");
        }
        byte[] bArrM442a = go.m442a();
        if (bArrM442a != null) {
            ex.j jVar = new ex.j();
            jVar.a(a.a(bArrM442a));
            gwVar.a(jVar.m303a(), (String) null);
        }
        return gwVar;
    }

    @Override // com.xiaomi.push.hi
    /* renamed from: a, reason: collision with other method in class */
    protected void mo460a(boolean z2) throws hm {
        if (this.f506a != null) {
            gq gqVarA = a(z2);
            com.xiaomi.channel.commonutils.logger.b.m91a("[Slim] SND ping id=" + gqVarA.e());
            b(gqVarA);
            f();
            return;
        }
        throw new hm("The BlobWriter is null.");
    }

    @Override // com.xiaomi.push.hb
    public synchronized void a(bf.b bVar) {
        gp.a(bVar, c(), this);
    }

    @Override // com.xiaomi.push.hb
    public synchronized void a(String str, String str2) {
        gp.a(str, str2, this);
    }

    @Override // com.xiaomi.push.hi
    protected synchronized void a(int i2, Exception exc) {
        try {
            gs gsVar = this.f23854a;
            if (gsVar != null) {
                gsVar.b();
                this.f23854a = null;
            }
            gt gtVar = this.f506a;
            if (gtVar != null) {
                try {
                    gtVar.b();
                } catch (Exception e2) {
                    com.xiaomi.channel.commonutils.logger.b.d("SlimConnection shutdown cause exception: " + e2);
                }
                this.f506a = null;
            }
            this.f508a = null;
            super.a(i2, exc);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.xiaomi.push.hi, com.xiaomi.push.hb
    public void a(gq[] gqVarArr) throws hm {
        for (gq gqVar : gqVarArr) {
            b(gqVar);
        }
    }

    @Override // com.xiaomi.push.hb
    @Deprecated
    public void a(hs hsVar) throws hm {
        b(gq.a(hsVar, (String) null));
    }

    @Override // com.xiaomi.push.hi
    /* renamed from: a */
    protected synchronized void mo480a() {
        h();
        this.f506a.a();
    }

    void a(gq gqVar) {
        if (gqVar == null) {
            return;
        }
        if (com.xiaomi.push.service.e.a(gqVar)) {
            gq gqVar2 = new gq();
            gqVar2.a(gqVar.a());
            gqVar2.a("SYNC", "ACK_RTT");
            gqVar2.a(gqVar.e());
            gqVar2.b(gqVar.m450b());
            gqVar2.a(gqVar.m453c());
            XMPushService xMPushService = ((hb) this).f521a;
            xMPushService.a(new com.xiaomi.push.service.bu(xMPushService, gqVar2));
        }
        if (gqVar.m447a()) {
            com.xiaomi.channel.commonutils.logger.b.m91a("[Slim] RCV blob chid=" + gqVar.a() + "; id=" + gqVar.e() + "; errCode=" + gqVar.b() + "; err=" + gqVar.m454c());
        }
        if (gqVar.a() == 0) {
            if ("PING".equals(gqVar.m444a())) {
                com.xiaomi.channel.commonutils.logger.b.m91a("[Slim] RCV ping id=" + gqVar.e());
                g();
            } else if ("CLOSE".equals(gqVar.m444a())) {
                c(13, null);
            }
        }
        Iterator<hb.a> it = ((hb) this).f525a.values().iterator();
        while (it.hasNext()) {
            it.next().a(gqVar);
        }
    }
}

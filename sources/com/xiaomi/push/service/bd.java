package com.xiaomi.push.service;

import android.os.RemoteException;
import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.tekartik.sqflite.Constant;
import com.xiaomi.push.cz;
import com.xiaomi.push.dd;
import com.xiaomi.push.ef;
import com.xiaomi.push.ex;
import com.xiaomi.push.ge;
import com.xiaomi.push.go;
import com.xiaomi.push.gq;
import com.xiaomi.push.hc;
import com.xiaomi.push.hp;
import com.xiaomi.push.hq;
import com.xiaomi.push.hr;
import com.xiaomi.push.hs;
import com.xiaomi.push.ig;
import com.xiaomi.push.service.bf;
import java.util.Date;

/* loaded from: classes4.dex */
public class bd {

    /* renamed from: a, reason: collision with root package name */
    private XMPushService f24494a;

    bd(XMPushService xMPushService) {
        this.f24494a = xMPushService;
    }

    private void c(gq gqVar) {
        bf.b bVarA;
        String strG = gqVar.g();
        String string = Integer.toString(gqVar.a());
        if (TextUtils.isEmpty(strG) || TextUtils.isEmpty(string) || (bVarA = bf.a().a(string, strG)) == null) {
            return;
        }
        ig.a(this.f24494a, bVarA.f1040a, gqVar.c(), true, true, System.currentTimeMillis());
    }

    public void a(hs hsVar) {
        if (!AlcsPalConst.MODEL_TYPE_TGMESH.equals(hsVar.k())) {
            b(hsVar);
        }
        String strK = hsVar.k();
        if (TextUtils.isEmpty(strK)) {
            strK = "1";
            hsVar.l("1");
        }
        if (strK.equals("0")) {
            com.xiaomi.channel.commonutils.logger.b.m91a("Received wrong packet with chid = 0 : " + hsVar.mo485a());
        }
        if (hsVar instanceof hq) {
            hp hpVarA = hsVar.a("kick");
            if (hpVarA != null) {
                String strL = hsVar.l();
                String strA = hpVarA.a("type");
                String strA2 = hpVarA.a("reason");
                com.xiaomi.channel.commonutils.logger.b.m91a("kicked by server, chid=" + strK + " res=" + bf.b.a(strL) + " type=" + strA + " reason=" + strA2);
                if (!"wait".equals(strA)) {
                    this.f24494a.a(strK, strL, 3, strA2, strA);
                    bf.a().m759a(strK, strL);
                    return;
                }
                bf.b bVarA = bf.a().a(strK, strL);
                if (bVarA != null) {
                    this.f24494a.a(bVarA);
                    bVarA.a(bf.c.unbind, 3, 0, strA2, strA);
                    return;
                }
                return;
            }
        } else if (hsVar instanceof hr) {
            hr hrVar = (hr) hsVar;
            if ("redir".equals(hrVar.b())) {
                hp hpVarA2 = hrVar.a(DispatchConstants.HOSTS);
                if (hpVarA2 != null) {
                    a(hpVarA2);
                    return;
                }
                return;
            }
        }
        this.f24494a.m713b().a(this.f24494a, strK, hsVar);
    }

    public void b(gq gqVar) throws RemoteException {
        String strM444a = gqVar.m444a();
        if (gqVar.a() == 0) {
            if ("PING".equals(strM444a)) {
                byte[] bArrM448a = gqVar.m448a();
                if (bArrM448a != null && bArrM448a.length > 0) {
                    ex.j jVarA = ex.j.a(bArrM448a);
                    if (jVarA.m391b()) {
                        bw.a().a(jVarA.m389a());
                    }
                }
                if (!"com.xiaomi.xmsf".equals(this.f24494a.getPackageName())) {
                    this.f24494a.m710a();
                }
                if ("1".equals(gqVar.e())) {
                    com.xiaomi.channel.commonutils.logger.b.m91a("received a server ping");
                } else {
                    go.b();
                }
                this.f24494a.m714b();
                return;
            }
            if (!"SYNC".equals(strM444a)) {
                if ("NOTIFY".equals(gqVar.m444a())) {
                    ex.h hVarA = ex.h.a(gqVar.m448a());
                    com.xiaomi.channel.commonutils.logger.b.m91a("notify by server err = " + hVarA.c() + " desc = " + hVarA.m383a());
                    return;
                }
                return;
            }
            if ("CONF".equals(gqVar.m451b())) {
                bw.a().a(ex.b.a(gqVar.m448a()));
                return;
            }
            if (TextUtils.equals("U", gqVar.m451b())) {
                ex.k kVarA = ex.k.a(gqVar.m448a());
                ef.a(this.f24494a).a(kVarA.m393a(), kVarA.m396b(), new Date(kVarA.m392a()), new Date(kVarA.m395b()), kVarA.c() * 1024, kVarA.e());
                gq gqVar2 = new gq();
                gqVar2.a(0);
                gqVar2.a(gqVar.m444a(), "UCA");
                gqVar2.a(gqVar.e());
                XMPushService xMPushService = this.f24494a;
                xMPushService.a(new bu(xMPushService, gqVar2));
                return;
            }
            if (TextUtils.equals("P", gqVar.m451b())) {
                ex.i iVarA = ex.i.a(gqVar.m448a());
                gq gqVar3 = new gq();
                gqVar3.a(0);
                gqVar3.a(gqVar.m444a(), "PCA");
                gqVar3.a(gqVar.e());
                ex.i iVar = new ex.i();
                if (iVarA.m387a()) {
                    iVar.a(iVarA.m386a());
                }
                gqVar3.a(iVar.m303a(), (String) null);
                XMPushService xMPushService2 = this.f24494a;
                xMPushService2.a(new bu(xMPushService2, gqVar3));
                com.xiaomi.channel.commonutils.logger.b.m91a("ACK msgP: id = " + gqVar.e());
                return;
            }
            return;
        }
        String string = Integer.toString(gqVar.a());
        if ("SECMSG".equals(gqVar.m444a())) {
            if (!gqVar.m447a()) {
                this.f24494a.m713b().a(this.f24494a, string, gqVar);
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.m91a("Recv SECMSG errCode = " + gqVar.b() + " errStr = " + gqVar.m454c());
            return;
        }
        if (!"BIND".equals(strM444a)) {
            if ("KICK".equals(strM444a)) {
                ex.g gVarA = ex.g.a(gqVar.m448a());
                String strG = gqVar.g();
                String strM378a = gVarA.m378a();
                String strM380b = gVarA.m380b();
                com.xiaomi.channel.commonutils.logger.b.m91a("kicked by server, chid=" + string + " res= " + bf.b.a(strG) + " type=" + strM378a + " reason=" + strM380b);
                if (!"wait".equals(strM378a)) {
                    this.f24494a.a(string, strG, 3, strM380b, strM378a);
                    bf.a().m759a(string, strG);
                    return;
                }
                bf.b bVarA = bf.a().a(string, strG);
                if (bVarA != null) {
                    this.f24494a.a(bVarA);
                    bVarA.a(bf.c.unbind, 3, 0, strM380b, strM378a);
                    return;
                }
                return;
            }
            return;
        }
        ex.d dVarA = ex.d.a(gqVar.m448a());
        String strG2 = gqVar.g();
        bf.b bVarA2 = bf.a().a(string, strG2);
        if (bVarA2 == null) {
            return;
        }
        if (dVarA.m354a()) {
            com.xiaomi.channel.commonutils.logger.b.m91a("SMACK: channel bind succeeded, chid=" + gqVar.a());
            bVarA2.a(bf.c.binded, 1, 0, (String) null, (String) null);
            return;
        }
        String strM353a = dVarA.m353a();
        if ("auth".equals(strM353a)) {
            if ("invalid-sig".equals(dVarA.m355b())) {
                com.xiaomi.channel.commonutils.logger.b.m91a("SMACK: bind error invalid-sig token = " + bVarA2.f24499c + " sec = " + bVarA2.f24504h);
                go.a(0, ge.BIND_INVALID_SIG.a(), 1, null, 0);
            }
            bVarA2.a(bf.c.unbind, 1, 5, dVarA.m355b(), strM353a);
            bf.a().m759a(string, strG2);
        } else if (Constant.PARAM_CANCEL.equals(strM353a)) {
            bVarA2.a(bf.c.unbind, 1, 7, dVarA.m355b(), strM353a);
            bf.a().m759a(string, strG2);
        } else if ("wait".equals(strM353a)) {
            this.f24494a.a(bVarA2);
            bVarA2.a(bf.c.unbind, 1, 7, dVarA.m355b(), strM353a);
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("SMACK: channel bind failed, chid=" + string + " reason=" + dVarA.m355b());
    }

    public void a(gq gqVar) {
        if (5 != gqVar.a()) {
            c(gqVar);
        }
        try {
            b(gqVar);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a("handle Blob chid = " + gqVar.a() + " cmd = " + gqVar.m444a() + " packetid = " + gqVar.e() + " failure ", e2);
        }
    }

    private void a(hp hpVar) {
        String strC = hpVar.c();
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        String[] strArrSplit = strC.split(com.alipay.sdk.m.u.i.f9802b);
        cz czVarA = dd.a().a(hc.a(), false);
        if (czVarA == null || strArrSplit.length <= 0) {
            return;
        }
        czVarA.a(strArrSplit);
        this.f24494a.a(20, (Exception) null);
        this.f24494a.a(true);
    }

    private void b(hs hsVar) {
        bf.b bVarA;
        String strL = hsVar.l();
        String strK = hsVar.k();
        if (TextUtils.isEmpty(strL) || TextUtils.isEmpty(strK) || (bVarA = bf.a().a(strK, strL)) == null) {
            return;
        }
        ig.a(this.f24494a, bVarA.f1040a, ig.a(hsVar.mo485a()), true, true, System.currentTimeMillis());
    }
}

package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.bl;
import com.xiaomi.push.kp;
import com.xiaomi.push.service.XMPushService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public class gm {

    /* renamed from: a, reason: collision with root package name */
    private int f23838a;

    /* renamed from: a, reason: collision with other field name */
    private long f477a;

    /* renamed from: a, reason: collision with other field name */
    private gl f479a;

    /* renamed from: a, reason: collision with other field name */
    private String f480a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f481a = false;

    /* renamed from: a, reason: collision with other field name */
    private bl f478a = bl.a();

    static class a {

        /* renamed from: a, reason: collision with root package name */
        static final gm f23839a = new gm();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static gm m436a() {
        return a.f23839a;
    }

    boolean b() {
        m437a();
        return this.f481a && this.f478a.m216a() > 0;
    }

    public static gl a() {
        gl glVar;
        gm gmVar = a.f23839a;
        synchronized (gmVar) {
            glVar = gmVar.f479a;
        }
        return glVar;
    }

    public synchronized void a(XMPushService xMPushService) {
        this.f479a = new gl(xMPushService);
        this.f480a = "";
        com.xiaomi.push.service.bw.a().a(new gn(this));
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m441a() {
        return this.f481a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m440a(int i2) {
        if (i2 > 0) {
            int i3 = i2 * 1000;
            if (i3 > 604800000) {
                i3 = 604800000;
            }
            if (this.f23838a == i3 && this.f481a) {
                return;
            }
            this.f481a = true;
            this.f477a = System.currentTimeMillis();
            this.f23838a = i3;
            com.xiaomi.channel.commonutils.logger.b.c("enable dot duration = " + i3 + " start = " + this.f477a);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m437a() {
        if (!this.f481a || System.currentTimeMillis() - this.f477a <= this.f23838a) {
            return;
        }
        this.f481a = false;
        this.f477a = 0L;
    }

    /* renamed from: a, reason: collision with other method in class */
    synchronized gg m439a() {
        gg ggVarA;
        if (b()) {
            ggVarA = a(!bg.e(this.f479a.f474a) ? 375 : 750);
        } else {
            ggVarA = null;
        }
        return ggVarA;
    }

    private gg a(int i2) {
        ArrayList arrayList = new ArrayList();
        gg ggVar = new gg(this.f480a, arrayList);
        if (!bg.e(this.f479a.f474a)) {
            ggVar.a(i.i(this.f479a.f474a));
        }
        kr krVar = new kr(i2);
        kj kjVarA = new kp.a().a(krVar);
        try {
            ggVar.b(kjVarA);
        } catch (kd unused) {
        }
        LinkedList<bl.a> linkedListM217a = this.f478a.m217a();
        while (linkedListM217a.size() > 0) {
            try {
                gf gfVarA = a(linkedListM217a.getLast());
                if (gfVarA != null) {
                    gfVarA.b(kjVarA);
                }
                if (krVar.a_() > i2) {
                    break;
                }
                if (gfVarA != null) {
                    arrayList.add(gfVarA);
                }
                linkedListM217a.removeLast();
            } catch (kd | NoSuchElementException unused2) {
            }
        }
        return ggVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    synchronized gf m438a() {
        gf gfVar;
        gfVar = new gf();
        gfVar.a(bg.m204a((Context) this.f479a.f474a));
        gfVar.f451a = (byte) 0;
        gfVar.f455b = 1;
        gfVar.d((int) (System.currentTimeMillis() / 1000));
        return gfVar;
    }

    private gf a(bl.a aVar) {
        if (aVar.f213a == 0) {
            Object obj = aVar.f214a;
            if (obj instanceof gf) {
                return (gf) obj;
            }
            return null;
        }
        gf gfVarM438a = m438a();
        gfVarM438a.a(ge.CHANNEL_STATS_COUNTER.a());
        gfVarM438a.c(aVar.f213a);
        gfVarM438a.c(aVar.f215a);
        return gfVarM438a;
    }

    synchronized void a(gf gfVar) {
        this.f478a.a(gfVar);
    }
}

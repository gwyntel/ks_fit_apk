package com.xiaomi.push.service;

import com.xiaomi.push.gm;
import com.xiaomi.push.service.XMPushService.e;
import java.util.Objects;

/* loaded from: classes4.dex */
class bp {

    /* renamed from: d, reason: collision with root package name */
    private static int f24551d = 300000;

    /* renamed from: a, reason: collision with other field name */
    private XMPushService f1054a;

    /* renamed from: b, reason: collision with root package name */
    private int f24553b = 0;

    /* renamed from: c, reason: collision with root package name */
    private int f24554c = 0;

    /* renamed from: a, reason: collision with root package name */
    private int f24552a = 500;

    /* renamed from: a, reason: collision with other field name */
    private long f1053a = 0;

    public bp(XMPushService xMPushService) {
        this.f1054a = xMPushService;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m765a() {
        this.f1053a = System.currentTimeMillis();
        this.f1054a.a(1);
        this.f24553b = 0;
    }

    public void a(boolean z2) {
        if (!this.f1054a.m711a()) {
            com.xiaomi.channel.commonutils.logger.b.c("should not reconnect as no client or network.");
            return;
        }
        if (z2) {
            if (!this.f1054a.m712a(1)) {
                this.f24553b++;
            }
            this.f1054a.a(1);
            com.xiaomi.channel.commonutils.logger.b.m92a("ReconnectionManager", "-->tryReconnect(): exec ConnectJob");
            XMPushService xMPushService = this.f1054a;
            Objects.requireNonNull(xMPushService);
            xMPushService.a(xMPushService.new e());
            return;
        }
        if (this.f1054a.m712a(1)) {
            return;
        }
        int iA = a();
        this.f24553b++;
        com.xiaomi.channel.commonutils.logger.b.m91a("schedule reconnect in " + iA + "ms");
        XMPushService xMPushService2 = this.f1054a;
        Objects.requireNonNull(xMPushService2);
        xMPushService2.a(xMPushService2.new e(), (long) iA);
        if (this.f24553b == 2 && gm.m436a().m441a()) {
            ao.b();
        }
        if (this.f24553b == 3) {
            ao.a();
        }
    }

    private int a() {
        double d2;
        if (this.f24553b > 8) {
            return com.alipay.sdk.m.e0.a.f9235a;
        }
        double dRandom = (Math.random() * 2.0d) + 1.0d;
        int i2 = this.f24553b;
        if (i2 > 4) {
            d2 = 60000.0d;
        } else {
            if (i2 <= 1) {
                if (this.f1053a == 0) {
                    return 0;
                }
                if (System.currentTimeMillis() - this.f1053a < 310000) {
                    int i3 = this.f24552a;
                    int i4 = f24551d;
                    if (i3 >= i4) {
                        return i3;
                    }
                    int i5 = this.f24554c + 1;
                    this.f24554c = i5;
                    if (i5 >= 4) {
                        return i4;
                    }
                    this.f24552a = (int) (i3 * 1.5d);
                    return i3;
                }
                this.f24552a = 1000;
                this.f24554c = 0;
                return 0;
            }
            d2 = 10000.0d;
        }
        return (int) (dRandom * d2);
    }
}

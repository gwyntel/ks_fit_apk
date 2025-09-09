package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.is;
import com.xiaomi.push.service.az;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class f implements AbstractPushManager {

    /* renamed from: a, reason: collision with root package name */
    private static volatile f f23409a;

    /* renamed from: a, reason: collision with other field name */
    private Context f147a;

    /* renamed from: a, reason: collision with other field name */
    private PushConfiguration f148a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f150a = false;

    /* renamed from: a, reason: collision with other field name */
    private Map<e, AbstractPushManager> f149a = new HashMap();

    private f(Context context) {
        this.f147a = context.getApplicationContext();
    }

    public boolean b(e eVar) {
        int i2 = h.f23411a[eVar.ordinal()];
        boolean openCOSPush = false;
        if (i2 == 1) {
            PushConfiguration pushConfiguration = this.f148a;
            if (pushConfiguration != null) {
                return pushConfiguration.getOpenHmsPush();
            }
            return false;
        }
        if (i2 == 2) {
            PushConfiguration pushConfiguration2 = this.f148a;
            if (pushConfiguration2 != null) {
                return pushConfiguration2.getOpenFCMPush();
            }
            return false;
        }
        if (i2 == 3) {
            PushConfiguration pushConfiguration3 = this.f148a;
            if (pushConfiguration3 != null) {
                openCOSPush = pushConfiguration3.getOpenCOSPush();
            }
        } else if (i2 != 4) {
            return false;
        }
        PushConfiguration pushConfiguration4 = this.f148a;
        return pushConfiguration4 != null ? pushConfiguration4.getOpenFTOSPush() : openCOSPush;
    }

    @Override // com.xiaomi.mipush.sdk.AbstractPushManager
    public void register() {
        com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH : assemble push register");
        if (this.f149a.size() <= 0) {
            a();
        }
        if (this.f149a.size() > 0) {
            for (AbstractPushManager abstractPushManager : this.f149a.values()) {
                if (abstractPushManager != null) {
                    abstractPushManager.register();
                }
            }
            i.m160a(this.f147a);
        }
    }

    @Override // com.xiaomi.mipush.sdk.AbstractPushManager
    public void unregister() {
        com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH : assemble push unregister");
        for (AbstractPushManager abstractPushManager : this.f149a.values()) {
            if (abstractPushManager != null) {
                abstractPushManager.unregister();
            }
        }
        this.f149a.clear();
    }

    public static f a(Context context) {
        if (f23409a == null) {
            synchronized (f.class) {
                try {
                    if (f23409a == null) {
                        f23409a = new f(context);
                    }
                } finally {
                }
            }
        }
        return f23409a;
    }

    public void a(PushConfiguration pushConfiguration) {
        this.f148a = pushConfiguration;
        this.f150a = az.a(this.f147a).a(is.AggregatePushSwitch.a(), true);
        if (this.f148a.getOpenHmsPush() || this.f148a.getOpenFCMPush() || this.f148a.getOpenCOSPush() || this.f148a.getOpenFTOSPush()) {
            az.a(this.f147a).a(new g(this, 101, "assemblePush"));
        }
    }

    public void a(e eVar, AbstractPushManager abstractPushManager) {
        if (abstractPushManager != null) {
            if (this.f149a.containsKey(eVar)) {
                this.f149a.remove(eVar);
            }
            this.f149a.put(eVar, abstractPushManager);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m157a(e eVar) {
        this.f149a.remove(eVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m158a(e eVar) {
        return this.f149a.containsKey(eVar);
    }

    public AbstractPushManager a(e eVar) {
        return this.f149a.get(eVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x019b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a() {
        /*
            Method dump skipped, instructions count: 488
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mipush.sdk.f.a():void");
    }
}

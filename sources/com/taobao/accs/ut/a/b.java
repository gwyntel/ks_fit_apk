package com.taobao.accs.ut.a;

import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public String f20291a;

    /* renamed from: b, reason: collision with root package name */
    public String f20292b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f20293c;

    /* renamed from: d, reason: collision with root package name */
    public String f20294d;

    /* renamed from: e, reason: collision with root package name */
    public String f20295e;

    /* renamed from: f, reason: collision with root package name */
    private final String f20296f = "BindUser";

    /* renamed from: g, reason: collision with root package name */
    private boolean f20297g = false;

    private void b(String str) {
        String strValueOf;
        String str2;
        if (this.f20297g) {
            return;
        }
        this.f20297g = true;
        HashMap map = new HashMap();
        String str3 = null;
        try {
            str2 = this.f20291a;
            try {
                strValueOf = String.valueOf(221);
            } catch (Throwable th) {
                th = th;
                strValueOf = null;
            }
        } catch (Throwable th2) {
            th = th2;
            strValueOf = null;
        }
        try {
            map.put("device_id", this.f20291a);
            map.put("bind_date", this.f20292b);
            map.put("ret", this.f20293c ? "y" : "n");
            map.put("fail_reasons", this.f20294d);
            map.put("user_id", this.f20295e);
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.d("accs.BindUserStatistic", UTMini.getCommitInfo(66001, str2, (String) null, strValueOf, map), new Object[0]);
            }
            UTMini.getInstance().commitEvent(66001, str, str2, (Object) null, strValueOf, map);
        } catch (Throwable th3) {
            th = th3;
            str3 = str2;
            ALog.d("accs.BindUserStatistic", UTMini.getCommitInfo(66001, str3, (String) null, strValueOf, map) + " " + th.toString(), new Object[0]);
        }
    }

    public void a(String str) {
        this.f20294d = str;
    }

    public void a(int i2) {
        if (i2 == -4) {
            a("msg too large");
            return;
        }
        if (i2 == -3) {
            a("service not available");
            return;
        }
        if (i2 == -2) {
            a("param error");
            return;
        }
        if (i2 == -1) {
            a("network fail");
        } else if (i2 != 200) {
            if (i2 != 300) {
                a(String.valueOf(i2));
            } else {
                a("app not bind");
            }
        }
    }

    public void a() {
        b("BindUser");
    }
}

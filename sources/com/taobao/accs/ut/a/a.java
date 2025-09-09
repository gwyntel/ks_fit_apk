package com.taobao.accs.ut.a;

import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public String f20285a;

    /* renamed from: b, reason: collision with root package name */
    public String f20286b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f20287c;

    /* renamed from: d, reason: collision with root package name */
    public String f20288d;

    /* renamed from: e, reason: collision with root package name */
    private final String f20289e = "BindApp";

    /* renamed from: f, reason: collision with root package name */
    private boolean f20290f = false;

    private void b(String str) {
        String strValueOf;
        if (this.f20290f) {
            return;
        }
        this.f20290f = true;
        HashMap map = new HashMap();
        String str2 = null;
        try {
            String str3 = this.f20285a;
            try {
                strValueOf = String.valueOf(221);
                try {
                    map.put("device_id", this.f20285a);
                    map.put("bind_date", this.f20286b);
                    map.put("ret", this.f20287c ? "y" : "n");
                    map.put("fail_reasons", this.f20288d);
                    map.put("push_token", "");
                    UTMini.getInstance().commitEvent(66001, str, str3, (Object) null, strValueOf, map);
                } catch (Throwable th) {
                    th = th;
                    str2 = str3;
                    ALog.d("BindAppStatistic", UTMini.getCommitInfo(66001, str2, (String) null, strValueOf, map) + " " + th.toString(), new Object[0]);
                }
            } catch (Throwable th2) {
                th = th2;
                strValueOf = null;
            }
        } catch (Throwable th3) {
            th = th3;
            strValueOf = null;
        }
    }

    public void a(String str) {
        this.f20288d = str;
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
        b("BindApp");
    }
}

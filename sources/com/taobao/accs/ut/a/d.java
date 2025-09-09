package com.taobao.accs.ut.a;

import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public String f20309a;

    /* renamed from: b, reason: collision with root package name */
    public String f20310b;

    /* renamed from: c, reason: collision with root package name */
    public String f20311c;

    /* renamed from: d, reason: collision with root package name */
    public String f20312d;

    /* renamed from: e, reason: collision with root package name */
    public String f20313e;

    /* renamed from: f, reason: collision with root package name */
    public String f20314f;

    /* renamed from: g, reason: collision with root package name */
    public String f20315g;

    /* renamed from: i, reason: collision with root package name */
    public String f20317i;

    /* renamed from: j, reason: collision with root package name */
    private final String f20318j = "receiveMessage";

    /* renamed from: h, reason: collision with root package name */
    public boolean f20316h = false;

    /* renamed from: k, reason: collision with root package name */
    private boolean f20319k = false;

    public void a() {
        String str;
        String strValueOf;
        Throwable th;
        if (this.f20319k) {
            return;
        }
        this.f20319k = true;
        HashMap map = new HashMap();
        try {
            str = this.f20309a;
        } catch (Throwable th2) {
            th = th2;
            str = null;
            strValueOf = null;
        }
        try {
            strValueOf = String.valueOf(221);
            try {
                map.put("device_id", this.f20309a);
                map.put("data_id", this.f20310b);
                map.put("receive_date", this.f20311c);
                map.put("to_bz_date", this.f20312d);
                map.put("service_id", this.f20313e);
                map.put("data_length", this.f20314f);
                map.put("msg_type", this.f20315g);
                map.put("repeat", this.f20316h ? "y" : "n");
                map.put("user_id", this.f20317i);
                UTMini.getInstance().commitEvent(66001, "receiveMessage", str, (Object) null, strValueOf, map);
            } catch (Throwable th3) {
                th = th3;
                ALog.d("ReceiveMessage", UTMini.getCommitInfo(66001, str, (String) null, strValueOf, map) + " " + th.toString(), new Object[0]);
            }
        } catch (Throwable th4) {
            th = th4;
            strValueOf = null;
            th = th;
            ALog.d("ReceiveMessage", UTMini.getCommitInfo(66001, str, (String) null, strValueOf, map) + " " + th.toString(), new Object[0]);
        }
    }
}

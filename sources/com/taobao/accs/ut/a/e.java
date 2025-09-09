package com.taobao.accs.ut.a;

import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    public String f20320a;

    /* renamed from: b, reason: collision with root package name */
    public String f20321b;

    /* renamed from: c, reason: collision with root package name */
    public String f20322c;

    /* renamed from: d, reason: collision with root package name */
    public String f20323d;

    /* renamed from: e, reason: collision with root package name */
    public String f20324e;

    /* renamed from: f, reason: collision with root package name */
    public String f20325f;

    /* renamed from: g, reason: collision with root package name */
    private final String f20326g = "sendAck";

    /* renamed from: h, reason: collision with root package name */
    private boolean f20327h = false;

    public void a() {
        String str;
        String strValueOf;
        Throwable th;
        if (this.f20327h) {
            return;
        }
        this.f20327h = true;
        HashMap map = new HashMap();
        try {
            str = this.f20320a;
        } catch (Throwable th2) {
            th = th2;
            str = null;
            strValueOf = null;
        }
        try {
            strValueOf = String.valueOf(221);
        } catch (Throwable th3) {
            th = th3;
            strValueOf = null;
            th = th;
            ALog.d("accs.SendAckStatistic", UTMini.getCommitInfo(66001, str, (String) null, strValueOf, map) + " " + th.toString(), new Object[0]);
        }
        try {
            map.put("device_id", this.f20320a);
            map.put("session_id", this.f20321b);
            map.put("data_id", this.f20322c);
            map.put("ack_date", this.f20323d);
            map.put("service_id", this.f20324e);
            map.put("fail_reasons", this.f20325f);
            UTMini.getInstance().commitEvent(66001, "sendAck", str, (Object) null, strValueOf, map);
        } catch (Throwable th4) {
            th = th4;
            ALog.d("accs.SendAckStatistic", UTMini.getCommitInfo(66001, str, (String) null, strValueOf, map) + " " + th.toString(), new Object[0]);
        }
    }
}

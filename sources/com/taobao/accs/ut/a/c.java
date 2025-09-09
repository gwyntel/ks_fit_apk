package com.taobao.accs.ut.a;

import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public int f20298a;

    /* renamed from: b, reason: collision with root package name */
    public int f20299b;

    /* renamed from: f, reason: collision with root package name */
    public String f20303f;

    /* renamed from: g, reason: collision with root package name */
    public String f20304g;

    /* renamed from: h, reason: collision with root package name */
    public long f20305h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f20306i;

    /* renamed from: j, reason: collision with root package name */
    public boolean f20307j;

    /* renamed from: k, reason: collision with root package name */
    private long f20308k = 0;

    /* renamed from: c, reason: collision with root package name */
    public boolean f20300c = false;

    /* renamed from: d, reason: collision with root package name */
    public int f20301d = 0;

    /* renamed from: e, reason: collision with root package name */
    public int f20302e = 0;

    public void a() {
        String strValueOf;
        String strValueOf2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (ALog.isPrintLog()) {
            ALog.d("MonitorStatistic", "commitUT interval:" + (jCurrentTimeMillis - this.f20308k) + " interval1:" + (jCurrentTimeMillis - this.f20305h), new Object[0]);
        }
        if (jCurrentTimeMillis - this.f20308k <= 1200000 || jCurrentTimeMillis - this.f20305h <= 60000) {
            return;
        }
        HashMap map = new HashMap();
        String str = null;
        try {
            String strValueOf3 = String.valueOf(this.f20301d);
            try {
                strValueOf2 = String.valueOf(this.f20302e);
                try {
                    strValueOf = String.valueOf(221);
                    try {
                        map.put("connStatus", String.valueOf(this.f20298a));
                        map.put("connType", String.valueOf(this.f20299b));
                        map.put("tcpConnected", String.valueOf(this.f20300c));
                        map.put("proxy", String.valueOf(this.f20303f));
                        map.put("startServiceTime", String.valueOf(this.f20305h));
                        map.put("commitTime", String.valueOf(jCurrentTimeMillis));
                        map.put("networkAvailable", String.valueOf(this.f20306i));
                        map.put("threadIsalive", String.valueOf(this.f20307j));
                        map.put("url", this.f20304g);
                        if (ALog.isPrintLog(ALog.Level.D)) {
                            try {
                                ALog.d("MonitorStatistic", UTMini.getCommitInfo(66001, strValueOf3, strValueOf2, strValueOf, map), new Object[0]);
                            } catch (Throwable th) {
                                th = th;
                                str = strValueOf3;
                                ALog.d("MonitorStatistic", UTMini.getCommitInfo(66001, str, strValueOf2, strValueOf, map) + " " + th.toString(), new Object[0]);
                            }
                        }
                        try {
                            UTMini.getInstance().commitEvent(66001, "MONITOR", strValueOf3, strValueOf2, strValueOf, map);
                            this.f20308k = jCurrentTimeMillis;
                        } catch (Throwable th2) {
                            th = th2;
                            str = strValueOf3;
                            strValueOf = strValueOf;
                            strValueOf2 = strValueOf2;
                            ALog.d("MonitorStatistic", UTMini.getCommitInfo(66001, str, strValueOf2, strValueOf, map) + " " + th.toString(), new Object[0]);
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    strValueOf = null;
                }
            } catch (Throwable th5) {
                th = th5;
                strValueOf = null;
                strValueOf2 = null;
            }
        } catch (Throwable th6) {
            th = th6;
            strValueOf = null;
            strValueOf2 = null;
        }
    }
}

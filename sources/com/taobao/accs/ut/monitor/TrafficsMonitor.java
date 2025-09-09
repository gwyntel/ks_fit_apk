package com.taobao.accs.ut.monitor;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.Dimension;
import anet.channel.statist.Measure;
import anet.channel.statist.Monitor;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public class TrafficsMonitor {

    /* renamed from: d, reason: collision with root package name */
    private Context f20340d;

    /* renamed from: a, reason: collision with root package name */
    private Map<String, List<a>> f20337a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private Map<String, String> f20338b = new HashMap<String, String>() { // from class: com.taobao.accs.ut.monitor.TrafficsMonitor.1
        {
            put("im", "512");
            put("motu", "513");
            put("acds", "514");
            put(GlobalClientInfo.AGOO_SERVICE_ID, "515");
            put(AgooConstants.AGOO_SERVICE_AGOOACK, "515");
            put("agooTokenReport", "515");
            put("accsSelf", "1000");
        }
    };

    /* renamed from: c, reason: collision with root package name */
    private int f20339c = 0;

    /* renamed from: e, reason: collision with root package name */
    private String f20341e = "";

    @Monitor(module = "NetworkSDK", monitorPoint = "TrafficStats")
    public static class StatTrafficMonitor extends BaseMonitor {

        @Dimension
        public String bizId;

        @Dimension
        public String date;

        @Dimension
        public String host;

        @Dimension
        public boolean isBackground;

        @Dimension
        public String serviceId;

        @Measure
        public long size;
    }

    public TrafficsMonitor(Context context) {
        this.f20340d = context;
    }

    private void b() {
        String str;
        boolean z2;
        synchronized (this.f20337a) {
            try {
                String strA = UtilityImpl.a(System.currentTimeMillis());
                if (TextUtils.isEmpty(this.f20341e) || this.f20341e.equals(strA)) {
                    str = strA;
                    z2 = false;
                } else {
                    str = this.f20341e;
                    z2 = true;
                }
                Iterator<String> it = this.f20337a.keySet().iterator();
                while (it.hasNext()) {
                    for (a aVar : this.f20337a.get(it.next())) {
                        if (aVar != null) {
                            com.taobao.accs.b.a aVarA = com.taobao.accs.b.a.a(this.f20340d);
                            String str2 = aVar.f20347e;
                            String str3 = aVar.f20345c;
                            aVarA.a(str2, str3, this.f20338b.get(str3), aVar.f20346d, aVar.f20348f, str);
                        }
                    }
                }
                ALog.Level level = ALog.Level.D;
                if (ALog.isPrintLog(level)) {
                    ALog.d("TrafficsMonitor", "savetoDay:" + str + " saveTraffics" + this.f20337a.toString(), new Object[0]);
                }
                if (z2) {
                    this.f20337a.clear();
                    c();
                } else if (ALog.isPrintLog(level)) {
                    ALog.d("TrafficsMonitor", "no need commit lastsaveDay:" + this.f20341e + " currday:" + strA, new Object[0]);
                }
                this.f20341e = strA;
                this.f20339c = 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void c() {
        List<a> listA = com.taobao.accs.b.a.a(this.f20340d).a(false);
        if (listA == null) {
            return;
        }
        try {
            for (a aVar : listA) {
                if (aVar != null) {
                    StatTrafficMonitor statTrafficMonitor = new StatTrafficMonitor();
                    statTrafficMonitor.bizId = aVar.f20344b;
                    statTrafficMonitor.date = aVar.f20343a;
                    statTrafficMonitor.host = aVar.f20347e;
                    statTrafficMonitor.isBackground = aVar.f20346d;
                    statTrafficMonitor.size = aVar.f20348f;
                    AppMonitor.getInstance().commitStat(statTrafficMonitor);
                }
            }
            com.taobao.accs.b.a.a(this.f20340d).a();
        } catch (Throwable th) {
            ALog.e("", th.toString(), new Object[0]);
            th.printStackTrace();
        }
    }

    public void a(a aVar) {
        String str;
        if (aVar == null || aVar.f20347e == null || aVar.f20348f <= 0) {
            return;
        }
        aVar.f20345c = TextUtils.isEmpty(aVar.f20345c) ? "accsSelf" : aVar.f20345c;
        synchronized (this.f20337a) {
            try {
                String str2 = this.f20338b.get(aVar.f20345c);
                if (TextUtils.isEmpty(str2)) {
                    return;
                }
                aVar.f20344b = str2;
                ALog.isPrintLog(ALog.Level.D);
                List<a> arrayList = this.f20337a.get(str2);
                if (arrayList != null) {
                    Iterator<a> it = arrayList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            arrayList.add(aVar);
                            break;
                        }
                        a next = it.next();
                        if (next.f20346d == aVar.f20346d && (str = next.f20347e) != null && str.equals(aVar.f20347e)) {
                            next.f20348f += aVar.f20348f;
                            break;
                        }
                    }
                } else {
                    arrayList = new ArrayList<>();
                    arrayList.add(aVar);
                }
                this.f20337a.put(str2, arrayList);
                int i2 = this.f20339c + 1;
                this.f20339c = i2;
                if (i2 >= 10) {
                    b();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        String f20343a;

        /* renamed from: b, reason: collision with root package name */
        String f20344b;

        /* renamed from: c, reason: collision with root package name */
        String f20345c;

        /* renamed from: d, reason: collision with root package name */
        boolean f20346d;

        /* renamed from: e, reason: collision with root package name */
        String f20347e;

        /* renamed from: f, reason: collision with root package name */
        long f20348f;

        public a(String str, boolean z2, String str2, long j2) {
            this.f20345c = str;
            this.f20346d = z2;
            this.f20347e = str2;
            this.f20348f = j2;
        }

        public String toString() {
            return "date:" + this.f20343a + " bizId:" + this.f20344b + " serviceId:" + this.f20345c + " host:" + this.f20347e + " isBackground:" + this.f20346d + " size:" + this.f20348f;
        }

        public a(String str, String str2, String str3, boolean z2, String str4, long j2) {
            this.f20343a = str;
            this.f20344b = str2;
            this.f20345c = str3;
            this.f20346d = z2;
            this.f20347e = str4;
            this.f20348f = j2;
        }
    }

    public void a() {
        try {
            synchronized (this.f20337a) {
                this.f20337a.clear();
            }
            List<a> listA = com.taobao.accs.b.a.a(this.f20340d).a(true);
            if (listA == null) {
                return;
            }
            Iterator<a> it = listA.iterator();
            while (it.hasNext()) {
                a(it.next());
            }
        } catch (Exception e2) {
            ALog.w("TrafficsMonitor", e2.toString(), new Object[0]);
        }
    }
}

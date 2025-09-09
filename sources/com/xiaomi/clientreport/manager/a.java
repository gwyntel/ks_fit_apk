package com.xiaomi.clientreport.manager;

import android.content.Context;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.processor.IEventProcessor;
import com.xiaomi.clientreport.processor.IPerfProcessor;
import com.xiaomi.push.ah;
import com.xiaomi.push.bp;
import com.xiaomi.push.by;
import com.xiaomi.push.bz;
import com.xiaomi.push.ca;
import com.xiaomi.push.cb;
import com.xiaomi.push.ce;
import com.xiaomi.push.j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final int f23339a;

    /* renamed from: a, reason: collision with other field name */
    private static volatile a f84a;

    /* renamed from: a, reason: collision with other field name */
    private Context f85a;

    /* renamed from: a, reason: collision with other field name */
    private Config f86a;

    /* renamed from: a, reason: collision with other field name */
    private IEventProcessor f87a;

    /* renamed from: a, reason: collision with other field name */
    private IPerfProcessor f88a;

    /* renamed from: a, reason: collision with other field name */
    private String f89a;

    /* renamed from: a, reason: collision with other field name */
    private ExecutorService f91a = Executors.newSingleThreadExecutor();

    /* renamed from: a, reason: collision with other field name */
    private HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> f90a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> f23340b = new HashMap<>();

    static {
        f23339a = j.m549a() ? 30 : 10;
    }

    private a(Context context) {
        this.f85a = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        try {
            this.f87a.b();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("we: " + e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        try {
            this.f88a.b();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("wp: " + e2.getMessage());
        }
    }

    private void f() {
        if (a(this.f85a).m101a().isEventUploadSwitchOpen()) {
            by byVar = new by(this.f85a);
            int eventUploadFrequency = (int) a(this.f85a).m101a().getEventUploadFrequency();
            if (eventUploadFrequency < 1800) {
                eventUploadFrequency = 1800;
            }
            if (System.currentTimeMillis() - ce.a(this.f85a).a("sp_client_report_status", "event_last_upload_time", 0L) > eventUploadFrequency * 1000) {
                ah.a(this.f85a).a(new h(this, byVar), 10);
            }
            synchronized (a.class) {
                try {
                    if (!ah.a(this.f85a).a((ah.a) byVar, eventUploadFrequency)) {
                        ah.a(this.f85a).m174a("100886");
                        ah.a(this.f85a).a((ah.a) byVar, eventUploadFrequency);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void g() {
        if (a(this.f85a).m101a().isPerfUploadSwitchOpen()) {
            bz bzVar = new bz(this.f85a);
            int perfUploadFrequency = (int) a(this.f85a).m101a().getPerfUploadFrequency();
            if (perfUploadFrequency < 1800) {
                perfUploadFrequency = 1800;
            }
            if (System.currentTimeMillis() - ce.a(this.f85a).a("sp_client_report_status", "perf_last_upload_time", 0L) > perfUploadFrequency * 1000) {
                ah.a(this.f85a).a(new i(this, bzVar), 15);
            }
            synchronized (a.class) {
                try {
                    if (!ah.a(this.f85a).a((ah.a) bzVar, perfUploadFrequency)) {
                        ah.a(this.f85a).m174a("100887");
                        ah.a(this.f85a).a((ah.a) bzVar, perfUploadFrequency);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void c() {
        if (m101a().isPerfUploadSwitchOpen()) {
            ca caVar = new ca();
            caVar.a(this.f88a);
            caVar.a(this.f85a);
            this.f91a.execute(caVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(EventClientReport eventClientReport) {
        IEventProcessor iEventProcessor = this.f87a;
        if (iEventProcessor != null) {
            iEventProcessor.mo104a(eventClientReport);
            if (a() >= 10) {
                d();
                ah.a(this.f85a).m174a("100888");
            } else {
                a(new d(this), f23339a);
            }
        }
    }

    public static a a(Context context) {
        if (f84a == null) {
            synchronized (a.class) {
                try {
                    if (f84a == null) {
                        f84a = new a(context);
                    }
                } finally {
                }
            }
        }
        return f84a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(PerfClientReport perfClientReport) {
        IPerfProcessor iPerfProcessor = this.f88a;
        if (iPerfProcessor != null) {
            iPerfProcessor.mo104a(perfClientReport);
            if (b() >= 10) {
                e();
                ah.a(this.f85a).m174a("100889");
            } else {
                a(new f(this), f23339a);
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized Config m101a() {
        try {
            if (this.f86a == null) {
                this.f86a = Config.defaultConfig(this.f85a);
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.f86a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int b() {
        HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> map = this.f90a;
        int i2 = 0;
        if (map != null) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                HashMap<String, com.xiaomi.clientreport.data.a> map2 = this.f90a.get(it.next());
                if (map2 != null) {
                    Iterator<String> it2 = map2.keySet().iterator();
                    while (it2.hasNext()) {
                        com.xiaomi.clientreport.data.a aVar = map2.get(it2.next());
                        if (aVar instanceof PerfClientReport) {
                            i2 = (int) (i2 + ((PerfClientReport) aVar).perfCounts);
                        }
                    }
                }
            }
        }
        return i2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m102a() {
        a(this.f85a).f();
        a(this.f85a).g();
    }

    public void a(String str) {
        this.f89a = str;
    }

    public void a(Config config, IEventProcessor iEventProcessor, IPerfProcessor iPerfProcessor) {
        this.f86a = config;
        this.f87a = iEventProcessor;
        this.f88a = iPerfProcessor;
        iEventProcessor.setEventMap(this.f23340b);
        this.f88a.setPerfMap(this.f90a);
    }

    public void a(boolean z2, boolean z3, long j2, long j3) {
        Config config = this.f86a;
        if (config != null) {
            if (z2 == config.isEventUploadSwitchOpen() && z3 == this.f86a.isPerfUploadSwitchOpen() && j2 == this.f86a.getEventUploadFrequency() && j3 == this.f86a.getPerfUploadFrequency()) {
                return;
            }
            long eventUploadFrequency = this.f86a.getEventUploadFrequency();
            long perfUploadFrequency = this.f86a.getPerfUploadFrequency();
            Config configBuild = Config.getBuilder().setAESKey(cb.a(this.f85a)).setEventEncrypted(this.f86a.isEventEncrypted()).setEventUploadSwitchOpen(z2).setEventUploadFrequency(j2).setPerfUploadSwitchOpen(z3).setPerfUploadFrequency(j3).build(this.f85a);
            this.f86a = configBuild;
            if (!configBuild.isEventUploadSwitchOpen()) {
                ah.a(this.f85a).m174a("100886");
            } else if (eventUploadFrequency != configBuild.getEventUploadFrequency()) {
                com.xiaomi.channel.commonutils.logger.b.c(this.f85a.getPackageName() + "reset event job " + configBuild.getEventUploadFrequency());
                f();
            }
            if (!this.f86a.isPerfUploadSwitchOpen()) {
                ah.a(this.f85a).m174a("100887");
                return;
            }
            if (perfUploadFrequency != configBuild.getPerfUploadFrequency()) {
                com.xiaomi.channel.commonutils.logger.b.c(this.f85a.getPackageName() + " reset perf job " + configBuild.getPerfUploadFrequency());
                g();
            }
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m103b() {
        if (m101a().isEventUploadSwitchOpen()) {
            ca caVar = new ca();
            caVar.a(this.f85a);
            caVar.a(this.f87a);
            this.f91a.execute(caVar);
        }
    }

    private void a(ah.a aVar, int i2) {
        ah.a(this.f85a).b(aVar, i2);
    }

    public void a(EventClientReport eventClientReport) {
        if (m101a().isEventUploadSwitchOpen()) {
            this.f91a.execute(new b(this, eventClientReport));
        }
    }

    public void a(PerfClientReport perfClientReport) {
        if (m101a().isPerfUploadSwitchOpen()) {
            this.f91a.execute(new c(this, perfClientReport));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a() {
        HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> map = this.f23340b;
        if (map == null) {
            return 0;
        }
        Iterator<String> it = map.keySet().iterator();
        int size = 0;
        while (it.hasNext()) {
            ArrayList<com.xiaomi.clientreport.data.a> arrayList = this.f23340b.get(it.next());
            size += arrayList != null ? arrayList.size() : 0;
        }
        return size;
    }

    public EventClientReport a(int i2, String str) {
        EventClientReport eventClientReport = new EventClientReport();
        eventClientReport.eventContent = str;
        eventClientReport.eventTime = System.currentTimeMillis();
        eventClientReport.eventType = i2;
        eventClientReport.eventId = bp.a(6);
        eventClientReport.production = 1000;
        eventClientReport.reportType = 1001;
        eventClientReport.clientInterfaceId = "E100004";
        eventClientReport.setAppPackageName(this.f85a.getPackageName());
        eventClientReport.setSdkVersion(this.f89a);
        return eventClientReport;
    }
}

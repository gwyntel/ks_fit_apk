package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.clientreport.data.Config;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.manager.ClientReportClient;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class fn {

    /* renamed from: a, reason: collision with root package name */
    private static a f23756a;

    /* renamed from: a, reason: collision with other field name */
    private static Map<String, ix> f422a;

    public interface a {
        void uploader(Context context, ir irVar);
    }

    public static int a(int i2) {
        if (i2 > 0) {
            return i2 + 1000;
        }
        return -1;
    }

    public static void a(a aVar) {
        f23756a = aVar;
    }

    private static void a(Context context, ir irVar) {
        if (m406a(context.getApplicationContext())) {
            com.xiaomi.push.service.cb.a(context.getApplicationContext(), irVar);
            return;
        }
        a aVar = f23756a;
        if (aVar != null) {
            aVar.uploader(context, irVar);
        }
    }

    public static EventClientReport a(String str) {
        EventClientReport eventClientReport = new EventClientReport();
        eventClientReport.production = 1000;
        eventClientReport.reportType = 1001;
        eventClientReport.clientInterfaceId = str;
        return eventClientReport;
    }

    public static PerfClientReport a() {
        PerfClientReport perfClientReport = new PerfClientReport();
        perfClientReport.production = 1000;
        perfClientReport.reportType = 1000;
        perfClientReport.clientInterfaceId = "P100000";
        return perfClientReport;
    }

    public static EventClientReport a(Context context, String str, String str2, int i2, long j2, String str3) {
        EventClientReport eventClientReportA = a(str);
        eventClientReportA.eventId = str2;
        eventClientReportA.eventType = i2;
        eventClientReportA.eventTime = j2;
        eventClientReportA.eventContent = str3;
        return eventClientReportA;
    }

    public static PerfClientReport a(Context context, int i2, long j2, long j3) {
        PerfClientReport perfClientReportA = a();
        perfClientReportA.code = i2;
        perfClientReportA.perfCounts = j2;
        perfClientReportA.perfLatencies = j3;
        return perfClientReportA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m404a(int i2) {
        if (i2 == 1000) {
            return "E100000";
        }
        if (i2 == 3000) {
            return "E100002";
        }
        if (i2 == 2000) {
            return "E100001";
        }
        if (i2 == 6000) {
            return "E100003";
        }
        return "";
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m406a(Context context) {
        return (context == null || TextUtils.isEmpty(context.getPackageName()) || !"com.xiaomi.xmsf".equals(context.getPackageName())) ? false : true;
    }

    public static void a(Context context, List<String> list) {
        if (list == null) {
            return;
        }
        try {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                ir irVarA = a(context, it.next());
                if (!com.xiaomi.push.service.ca.a(irVarA, false)) {
                    a(context, irVarA);
                }
            }
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.d(th.getMessage());
        }
    }

    public static ir a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ir irVar = new ir();
        irVar.d("category_client_report_data");
        irVar.a("push_sdk_channel");
        irVar.a(1L);
        irVar.b(str);
        irVar.a(true);
        irVar.b(System.currentTimeMillis());
        irVar.g(context.getPackageName());
        irVar.e("com.xiaomi.xmsf");
        irVar.f(com.xiaomi.push.service.ca.a());
        irVar.c("quality_support");
        return irVar;
    }

    public static void a(Context context, Config config) {
        ClientReportClient.init(context, config, new fl(context), new fm(context));
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m405a(Context context) {
        ClientReportClient.updateConfig(context, a(context));
    }

    public static Config a(Context context) {
        boolean zA = com.xiaomi.push.service.az.a(context).a(is.PerfUploadSwitch.a(), false);
        boolean zA2 = com.xiaomi.push.service.az.a(context).a(is.EventUploadNewSwitch.a(), false);
        return Config.getBuilder().setEventUploadSwitchOpen(zA2).setEventUploadFrequency(com.xiaomi.push.service.az.a(context).a(is.EventUploadFrequency.a(), 86400)).setPerfUploadSwitchOpen(zA).setPerfUploadFrequency(com.xiaomi.push.service.az.a(context).a(is.PerfUploadFrequency.a(), 86400)).build(context);
    }

    public static int a(Enum r1) {
        if (r1 != null) {
            if (r1 instanceof in) {
                return r1.ordinal() + 1001;
            }
            if (r1 instanceof ix) {
                return r1.ordinal() + 2001;
            }
            if (r1 instanceof fy) {
                return r1.ordinal() + 3001;
            }
        }
        return -1;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static ix m403a(String str) {
        if (f422a == null) {
            synchronized (ix.class) {
                try {
                    if (f422a == null) {
                        f422a = new HashMap();
                        for (ix ixVar : ix.values()) {
                            f422a.put(ixVar.f620a.toLowerCase(), ixVar);
                        }
                    }
                } finally {
                }
            }
        }
        ix ixVar2 = f422a.get(str.toLowerCase());
        return ixVar2 != null ? ixVar2 : ix.Invalid;
    }
}

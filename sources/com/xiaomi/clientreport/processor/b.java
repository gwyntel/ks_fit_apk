package com.xiaomi.clientreport.processor;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.push.cb;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class b implements IPerfProcessor {

    /* renamed from: a, reason: collision with root package name */
    protected Context f23350a;

    /* renamed from: a, reason: collision with other field name */
    private HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> f97a;

    public b(Context context) {
        this.f23350a = context;
    }

    private String c(com.xiaomi.clientreport.data.a aVar) {
        String strB = b(aVar);
        if (TextUtils.isEmpty(strB)) {
            return null;
        }
        for (int i2 = 0; i2 < 20; i2++) {
            String str = strB + i2;
            if (cb.m247a(this.f23350a, str)) {
                return str;
            }
        }
        return null;
    }

    @Override // com.xiaomi.clientreport.processor.c
    public void a() throws Throwable {
        cb.a(this.f23350a, "perf", "perfUploading");
        File[] fileArrM248a = cb.m248a(this.f23350a, "perfUploading");
        if (fileArrM248a == null || fileArrM248a.length <= 0) {
            return;
        }
        for (File file : fileArrM248a) {
            if (file != null) {
                List<String> listA = e.a(this.f23350a, file.getAbsolutePath());
                file.delete();
                a(listA);
            }
        }
    }

    @Override // com.xiaomi.clientreport.processor.d
    public void b() throws IOException {
        HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> map = this.f97a;
        if (map == null) {
            return;
        }
        if (map.size() > 0) {
            Iterator<String> it = this.f97a.keySet().iterator();
            while (it.hasNext()) {
                HashMap<String, com.xiaomi.clientreport.data.a> map2 = this.f97a.get(it.next());
                if (map2 != null && map2.size() > 0) {
                    com.xiaomi.clientreport.data.a[] aVarArr = new com.xiaomi.clientreport.data.a[map2.size()];
                    map2.values().toArray(aVarArr);
                    a(aVarArr);
                }
            }
        }
        this.f97a.clear();
    }

    @Override // com.xiaomi.clientreport.processor.IPerfProcessor
    public void setPerfMap(HashMap<String, HashMap<String, com.xiaomi.clientreport.data.a>> map) {
        this.f97a = map;
    }

    public void a(List<String> list) {
        cb.a(this.f23350a, list);
    }

    public void a(com.xiaomi.clientreport.data.a[] aVarArr) throws IOException {
        String strC = c(aVarArr[0]);
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        e.a(strC, aVarArr);
    }

    private String b(com.xiaomi.clientreport.data.a aVar) {
        String str;
        int i2 = aVar.production;
        String str2 = aVar.clientInterfaceId;
        if (i2 > 0 && !TextUtils.isEmpty(str2)) {
            str = String.valueOf(i2) + MqttTopic.MULTI_LEVEL_WILDCARD + str2;
        } else {
            str = "";
        }
        File externalFilesDir = this.f23350a.getExternalFilesDir("perf");
        if (externalFilesDir == null) {
            com.xiaomi.channel.commonutils.logger.b.d("cannot get folder when to write perf");
            return null;
        }
        if (!externalFilesDir.exists()) {
            externalFilesDir.mkdirs();
        }
        return new File(externalFilesDir, str).getAbsolutePath();
    }

    @Override // com.xiaomi.clientreport.processor.d
    /* renamed from: a */
    public void mo104a(com.xiaomi.clientreport.data.a aVar) {
        if ((aVar instanceof PerfClientReport) && this.f97a != null) {
            PerfClientReport perfClientReport = (PerfClientReport) aVar;
            String strA = a((com.xiaomi.clientreport.data.a) perfClientReport);
            String strA2 = e.a(perfClientReport);
            HashMap<String, com.xiaomi.clientreport.data.a> map = this.f97a.get(strA);
            if (map == null) {
                map = new HashMap<>();
            }
            PerfClientReport perfClientReport2 = (PerfClientReport) map.get(strA2);
            if (perfClientReport2 != null) {
                perfClientReport.perfCounts += perfClientReport2.perfCounts;
                perfClientReport.perfLatencies += perfClientReport2.perfLatencies;
            }
            map.put(strA2, perfClientReport);
            this.f97a.put(strA, map);
        }
    }

    public static String a(com.xiaomi.clientreport.data.a aVar) {
        return String.valueOf(aVar.production) + MqttTopic.MULTI_LEVEL_WILDCARD + aVar.clientInterfaceId;
    }
}

package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import com.facebook.internal.NativeProtocol;
import com.huawei.hms.framework.common.ExceptionCode;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.framework.common.hianalytics.HianalyticsHelper;
import com.huawei.hms.framework.common.hianalytics.LinkedHashMapPack;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e {

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ long f16266a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ ArrayList f16267b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ JSONArray f16268c;

        a(long j2, ArrayList arrayList, JSONArray jSONArray) {
            this.f16266a = j2;
            this.f16267b = arrayList;
            this.f16268c = jSONArray;
        }

        @Override // java.lang.Runnable
        public void run() {
            com.huawei.hms.framework.network.grs.g.j.a aVar = new com.huawei.hms.framework.network.grs.g.j.a();
            aVar.put("total_time", this.f16266a);
            Iterator it = this.f16267b.iterator();
            while (it.hasNext()) {
                d dVar = (d) it.next();
                if (dVar.o() || dVar.m()) {
                    aVar.put(e.b(dVar));
                    it.remove();
                    break;
                }
            }
            if (this.f16267b.size() > 0) {
                ArrayList arrayList = this.f16267b;
                d dVar2 = (d) arrayList.get(arrayList.size() - 1);
                aVar.put(e.b(dVar2));
                this.f16267b.remove(dVar2);
            }
            if (this.f16267b.size() > 0) {
                Iterator it2 = this.f16267b.iterator();
                while (it2.hasNext()) {
                    this.f16268c.put(new JSONObject(e.b((d) it2.next())));
                }
            }
            if (this.f16268c.length() > 0) {
                aVar.put("failed_info", this.f16268c.toString());
            }
            Logger.d("HaReportHelper", "grssdk report data to aiops is: %s", new JSONObject(aVar.get()));
            HianalyticsHelper.getInstance().onEvent(aVar.get(), "grs_request");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LinkedHashMap<String, String> b(d dVar) {
        LinkedHashMapPack linkedHashMapPack = new LinkedHashMapPack();
        Exception excD = dVar.d();
        if (excD != null) {
            linkedHashMapPack.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, ExceptionCode.getErrorCodeFromException(excD));
            linkedHashMapPack.put(CrashHianalyticsData.EXCEPTION_NAME, excD.getClass().getSimpleName());
            linkedHashMapPack.put("message", StringUtils.anonymizeMessage(excD.getMessage()));
        } else {
            linkedHashMapPack.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, dVar.b());
            linkedHashMapPack.put(CrashHianalyticsData.EXCEPTION_NAME, dVar.c());
        }
        try {
            linkedHashMapPack.put("domain", new URL(dVar.l()).getHost());
        } catch (MalformedURLException e2) {
            Logger.w("HaReportHelper", "report host MalformedURLException", e2);
        }
        linkedHashMapPack.put("req_start_time", dVar.h());
        linkedHashMapPack.put("req_end_time", dVar.g());
        linkedHashMapPack.put("req_total_time", dVar.i());
        return linkedHashMapPack.getAll();
    }

    public static void a(ArrayList<d> arrayList, long j2, JSONArray jSONArray, Context context) {
        if (context == null || arrayList == null || arrayList.size() <= 0 || !HianalyticsHelper.getInstance().isEnableReport(context)) {
            return;
        }
        HianalyticsHelper.getInstance().getReportExecutor().submit(new a(j2, arrayList, jSONArray));
    }
}

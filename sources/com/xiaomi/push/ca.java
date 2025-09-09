package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.clientreport.processor.IEventProcessor;
import com.xiaomi.clientreport.processor.IPerfProcessor;

/* loaded from: classes4.dex */
public class ca implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private Context f23525a;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.clientreport.processor.c f226a;

    public void a(com.xiaomi.clientreport.processor.c cVar) {
        this.f226a = cVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.xiaomi.clientreport.processor.c cVar = this.f226a;
            if (cVar != null) {
                cVar.a();
            }
            com.xiaomi.channel.commonutils.logger.b.c("begin read and send perf / event");
            com.xiaomi.clientreport.processor.c cVar2 = this.f226a;
            if (cVar2 instanceof IEventProcessor) {
                ce.a(this.f23525a).m249a("sp_client_report_status", "event_last_upload_time", System.currentTimeMillis());
            } else if (cVar2 instanceof IPerfProcessor) {
                ce.a(this.f23525a).m249a("sp_client_report_status", "perf_last_upload_time", System.currentTimeMillis());
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    public void a(Context context) {
        this.f23525a = context;
    }
}

package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.manager.ClientReportClient;

/* loaded from: classes4.dex */
public class fo {

    /* renamed from: a, reason: collision with root package name */
    private static volatile fo f23757a;

    /* renamed from: a, reason: collision with other field name */
    private Context f423a;

    private fo(Context context) {
        this.f423a = context;
    }

    public static fo a(Context context) {
        if (f23757a == null) {
            synchronized (fo.class) {
                try {
                    if (f23757a == null) {
                        f23757a = new fo(context);
                    }
                } finally {
                }
            }
        }
        return f23757a;
    }

    public void b(String str, String str2, String str3, String str4) {
        a(str, str2, str3, 5001, System.currentTimeMillis(), str4);
    }

    public void c(String str, String str2, String str3, String str4) {
        a(str, str2, str3, 4002, System.currentTimeMillis(), str4);
    }

    public void a(String str, String str2, String str3, int i2, long j2, String str4) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        EventClientReport eventClientReportA = fn.a(this.f423a, str2, str3, i2, j2, str4);
        eventClientReportA.setAppPackageName(str);
        eventClientReportA.setSdkVersion(BuildConfig.VERSION_NAME);
        a(eventClientReportA);
    }

    public void a(String str, String str2, String str3, int i2, String str4) {
        a(str, str2, str3, i2, System.currentTimeMillis(), str4);
    }

    public void a(String str, Intent intent, int i2, String str2) {
        if (intent == null) {
            return;
        }
        a(str, fn.m404a(intent.getIntExtra("eventMessageType", -1)), intent.getStringExtra(TmpConstant.RRESPONSE_MESSAGEID), i2, System.currentTimeMillis(), str2);
    }

    public void a(String str, String str2, String str3, String str4) {
        a(str, str2, str3, 5002, System.currentTimeMillis(), str4);
    }

    public void a(String str, Intent intent, String str2) {
        if (intent == null) {
            return;
        }
        a(str, fn.m404a(intent.getIntExtra("eventMessageType", -1)), intent.getStringExtra(TmpConstant.RRESPONSE_MESSAGEID), 5001, System.currentTimeMillis(), str2);
    }

    public void a(String str, int i2, long j2, long j3) {
        if (i2 < 0 || j3 < 0 || j2 <= 0) {
            return;
        }
        PerfClientReport perfClientReportA = fn.a(this.f423a, i2, j2, j3);
        perfClientReportA.setAppPackageName(str);
        perfClientReportA.setSdkVersion(BuildConfig.VERSION_NAME);
        a(perfClientReportA);
    }

    private void a(com.xiaomi.clientreport.data.a aVar) {
        if (aVar instanceof PerfClientReport) {
            ClientReportClient.reportPerf(this.f423a, (PerfClientReport) aVar);
        } else if (aVar instanceof EventClientReport) {
            ClientReportClient.reportEvent(this.f423a, (EventClientReport) aVar);
        }
    }
}

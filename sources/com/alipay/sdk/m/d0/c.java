package com.alipay.sdk.m.d0;

import android.content.Context;
import com.alipay.android.phone.mrpc.core.aa;
import com.alipay.android.phone.mrpc.core.h;
import com.alipay.android.phone.mrpc.core.w;
import com.alipay.tscenter.biz.rpc.deviceFp.BugTrackMessageService;
import com.alipay.tscenter.biz.rpc.report.general.DataReportService;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class c implements a {

    /* renamed from: d, reason: collision with root package name */
    public static c f9228d;

    /* renamed from: e, reason: collision with root package name */
    public static DataReportResult f9229e;

    /* renamed from: a, reason: collision with root package name */
    public w f9230a;

    /* renamed from: b, reason: collision with root package name */
    public BugTrackMessageService f9231b;

    /* renamed from: c, reason: collision with root package name */
    public DataReportService f9232c;

    public c(Context context, String str) {
        this.f9230a = null;
        this.f9231b = null;
        this.f9232c = null;
        aa aaVar = new aa();
        aaVar.a(str);
        h hVar = new h(context);
        this.f9230a = hVar;
        this.f9231b = (BugTrackMessageService) hVar.a(BugTrackMessageService.class, aaVar);
        this.f9232c = (DataReportService) this.f9230a.a(DataReportService.class, aaVar);
    }

    public static synchronized c a(Context context, String str) {
        try {
            if (f9228d == null) {
                f9228d = new c(context, str);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f9228d;
    }

    @Override // com.alipay.sdk.m.d0.a
    public boolean logCollect(String str) {
        BugTrackMessageService bugTrackMessageService;
        String strLogCollect;
        if (com.alipay.sdk.m.z.a.a(str) || (bugTrackMessageService = this.f9231b) == null) {
            return false;
        }
        try {
            strLogCollect = bugTrackMessageService.logCollect(com.alipay.sdk.m.z.a.f(str));
        } catch (Throwable unused) {
            strLogCollect = null;
        }
        if (com.alipay.sdk.m.z.a.a(strLogCollect)) {
            return false;
        }
        return ((Boolean) new JSONObject(strLogCollect).get("success")).booleanValue();
    }

    @Override // com.alipay.sdk.m.d0.a
    public DataReportResult a(DataReportRequest dataReportRequest) throws InterruptedException {
        if (dataReportRequest == null) {
            return null;
        }
        if (this.f9232c != null) {
            f9229e = null;
            new Thread(new b(this, dataReportRequest)).start();
            for (int i2 = com.alipay.sdk.m.e0.a.f9235a; f9229e == null && i2 >= 0; i2 -= 50) {
                Thread.sleep(50L);
            }
        }
        return f9229e;
    }
}

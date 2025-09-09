package com.alipay.sdk.m.d0;

import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;

/* loaded from: classes2.dex */
public class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ DataReportRequest f9226a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ c f9227b;

    public b(c cVar, DataReportRequest dataReportRequest) {
        this.f9227b = cVar;
        this.f9226a = dataReportRequest;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            DataReportResult unused = c.f9229e = this.f9227b.f9232c.reportData(this.f9226a);
        } catch (Throwable th) {
            DataReportResult unused2 = c.f9229e = new DataReportResult();
            c.f9229e.success = false;
            c.f9229e.resultCode = "static data rpc upload error, " + com.alipay.sdk.m.z.a.a(th);
            com.alipay.sdk.m.z.a.a(th);
        }
    }
}

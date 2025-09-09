package com.huawei.hms.scankit.p;

import android.os.Bundle;
import com.huawei.hms.feature.DynamicModuleInitializer;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.TimeZone;
import java.util.UUID;

/* loaded from: classes4.dex */
public final class v3 extends u3 {

    /* renamed from: o, reason: collision with root package name */
    private static String f17875o = "AiDetect";

    /* renamed from: p, reason: collision with root package name */
    private static String f17876p = "defaultDetect";

    /* renamed from: h, reason: collision with root package name */
    private int f17877h;

    /* renamed from: i, reason: collision with root package name */
    protected String f17878i;

    /* renamed from: j, reason: collision with root package name */
    protected String f17879j;

    /* renamed from: k, reason: collision with root package name */
    protected long f17880k;

    /* renamed from: l, reason: collision with root package name */
    protected long f17881l;

    /* renamed from: m, reason: collision with root package name */
    protected long f17882m;

    /* renamed from: n, reason: collision with root package name */
    protected boolean f17883n;

    class a extends SimpleDateFormat {
        a(String str) {
            super(str);
            setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }

    class b extends LinkedHashMap<String, String> {
        b() {
            v3.this.g();
            putAll(v3.this.f17845b);
        }
    }

    public v3(Bundle bundle, String str) {
        super(bundle, DynamicModuleInitializer.getContext().getApplicationContext());
        this.f17877h = XiaomiOAuthConstants.ERROR_NEED_AUTHORIZE;
        this.f17878i = u3.f17840d;
        this.f17879j = u3.f17841e;
        this.f17845b.put("callTime", new a("yyyyMMddHHmmss.SSS").format(Long.valueOf(System.currentTimeMillis())));
        this.f17845b.put(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, UUID.randomUUID().toString());
        this.f17845b.put("apiName", str);
    }

    private void j() {
        this.f17877h = XiaomiOAuthConstants.ERROR_NEED_AUTHORIZE;
        this.f17878i = u3.f17840d;
        this.f17879j = u3.f17841e;
    }

    public void a(long j2) {
        this.f17880k = j2;
    }

    public void c(int i2) {
        this.f17877h = i2;
    }

    public void h() {
        this.f17846c = System.currentTimeMillis();
    }

    public void i() {
        try {
            if (a()) {
                b bVar = new b();
                bVar.put("result", String.valueOf(this.f17877h));
                bVar.put("costTime", String.valueOf(System.currentTimeMillis() - this.f17846c));
                bVar.put("scanType", this.f17878i);
                bVar.put("sceneType", this.f17879j);
                if (this.f17881l != 0 && this.f17882m != 0) {
                    if (this.f17883n) {
                        bVar.put("recognizeMode", f17875o);
                        bVar.put("defaultDetectTime", String.valueOf(this.f17881l - this.f17880k));
                        bVar.put("aiDetectTime", String.valueOf(this.f17882m - this.f17881l));
                    } else {
                        bVar.put("recognizeMode", f17876p);
                        bVar.put("defaultDetectTime", String.valueOf(this.f17881l - this.f17880k));
                    }
                    bVar.put("recognizeSuccessTime", String.valueOf(this.f17882m - this.f17880k));
                }
                a4.b().b("60000", bVar);
                j();
            }
        } catch (NullPointerException unused) {
            o4.b("HaLog60000", "nullPoint");
        } catch (Exception unused2) {
            o4.b("HaLog60000", "logEnd Exception");
        }
    }

    public void a(long j2, long j3, boolean z2) {
        this.f17881l = j2;
        this.f17882m = j3;
        this.f17883n = z2;
    }

    public void a(HmsScan[] hmsScanArr) {
        if (hmsScanArr != null) {
            this.f17877h = hmsScanArr.length;
            for (HmsScan hmsScan : hmsScanArr) {
                this.f17878i = u3.a(hmsScan.scanType);
                this.f17879j = u3.b(hmsScan.scanTypeForm);
            }
        }
    }
}

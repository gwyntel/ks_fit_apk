package com.huawei.hms.scankit.p;

import android.content.Context;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.network.grs.GrsApp;
import java.util.Locale;

/* loaded from: classes4.dex */
public class a1 {

    /* renamed from: c, reason: collision with root package name */
    private static final String f16954c = "a1";

    /* renamed from: a, reason: collision with root package name */
    private String f16955a = "UNKNOWN";

    /* renamed from: b, reason: collision with root package name */
    private String f16956b;

    public a1(Context context, boolean z2) {
        this.f16956b = "UNKNOWN";
        a(context, z2);
        this.f16956b = this.f16956b.toUpperCase(Locale.ENGLISH);
    }

    private boolean b() {
        return !"UNKNOWN".equals(this.f16956b);
    }

    public String a() {
        return this.f16956b;
    }

    private void a(Context context, boolean z2) {
        if (context != null) {
            try {
                this.f16956b = GrsApp.getInstance().getIssueCountryCode(context);
                if (b()) {
                    Logger.i(f16954c, "getCountryCode unknown");
                }
            } catch (NullPointerException unused) {
                Logger.w(f16954c, "get CountryCode error");
            } catch (Exception unused2) {
                Logger.w(f16954c, "get CountryCode error");
            }
        }
    }
}

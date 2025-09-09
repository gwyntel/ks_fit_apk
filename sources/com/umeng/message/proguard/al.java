package com.umeng.message.proguard;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.device.panel.data.InvokeServiceData;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public final class al {

    /* renamed from: a, reason: collision with root package name */
    public final aq f22774a = new aq(AgooConstants.MESSAGE_NOTIFICATION);

    private long f() {
        return this.f22774a.b("rep_ts", 0L);
    }

    public final boolean a() {
        return this.f22774a.b("e_u", true);
    }

    public final long b() {
        return this.f22774a.b("req_ts", 0L);
    }

    public final boolean c() {
        return Math.abs(System.currentTimeMillis() - f()) < 60000;
    }

    public final String d() {
        String strB = this.f22774a.b(XiaomiOAuthConstants.EXTRA_INFO, "");
        if (!TextUtils.isEmpty(strB)) {
            try {
                return new String(as.a(strB));
            } catch (Exception unused) {
            }
        }
        return "";
    }

    public final boolean e() {
        return this.f22774a.b(InvokeServiceData.CALL_TYPE_SYNC, false);
    }

    public final void a(boolean z2) {
        this.f22774a.a("e_s", z2);
    }

    public final void b(boolean z2) {
        this.f22774a.a(InvokeServiceData.CALL_TYPE_SYNC, z2);
    }

    public final void a(long j2) {
        this.f22774a.a("rep_ts", j2);
    }
}

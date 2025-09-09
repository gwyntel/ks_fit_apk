package com.huawei.hms.hatool;

import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.xiaomi.mipush.sdk.Constants;
import java.util.Calendar;
import java.util.UUID;

/* loaded from: classes4.dex */
public class p0 {

    /* renamed from: a, reason: collision with root package name */
    private long f16448a = 1800000;

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f16449b = false;

    /* renamed from: c, reason: collision with root package name */
    private a f16450c = null;

    private class a {

        /* renamed from: a, reason: collision with root package name */
        String f16451a = UUID.randomUUID().toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");

        /* renamed from: b, reason: collision with root package name */
        boolean f16452b;

        /* renamed from: c, reason: collision with root package name */
        private long f16453c;

        a(long j2) {
            this.f16451a += OpenAccountUIConstants.UNDER_LINE + j2;
            this.f16453c = j2;
            this.f16452b = true;
            p0.this.f16449b = false;
        }

        private void b(long j2) {
            v.c("hmsSdk", "getNewSession() session is flush!");
            String string = UUID.randomUUID().toString();
            this.f16451a = string;
            this.f16451a = string.replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
            this.f16451a += OpenAccountUIConstants.UNDER_LINE + j2;
            this.f16453c = j2;
            this.f16452b = true;
        }

        void a(long j2) {
            if (p0.this.f16449b) {
                p0.this.f16449b = false;
                b(j2);
            } else if (b(this.f16453c, j2) || a(this.f16453c, j2)) {
                b(j2);
            } else {
                this.f16453c = j2;
                this.f16452b = false;
            }
        }

        private boolean a(long j2, long j3) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(j2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(j3);
            return (calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6)) ? false : true;
        }

        private boolean b(long j2, long j3) {
            return j3 - j2 >= p0.this.f16448a;
        }
    }

    public String a() {
        a aVar = this.f16450c;
        if (aVar != null) {
            return aVar.f16451a;
        }
        v.f("hmsSdk", "getSessionName(): session not prepared. onEvent() must be called first.");
        return "";
    }

    void a(long j2) {
        a aVar = this.f16450c;
        if (aVar != null) {
            aVar.a(j2);
        } else {
            v.c("hmsSdk", "Session is first flush");
            this.f16450c = new a(j2);
        }
    }

    public boolean b() {
        a aVar = this.f16450c;
        if (aVar != null) {
            return aVar.f16452b;
        }
        v.f("hmsSdk", "isFirstEvent(): session not prepared. onEvent() must be called first.");
        return false;
    }
}

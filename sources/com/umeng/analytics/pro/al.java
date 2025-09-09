package com.umeng.analytics.pro;

import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class al {

    /* renamed from: a, reason: collision with root package name */
    private String f21300a;

    /* renamed from: e, reason: collision with root package name */
    private boolean f21304e = false;

    /* renamed from: d, reason: collision with root package name */
    private int f21303d = -1;

    /* renamed from: c, reason: collision with root package name */
    private int f21302c = -1;

    /* renamed from: b, reason: collision with root package name */
    private int f21301b = -1;

    public al(String str) {
        this.f21300a = str;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        a();
    }

    private void a() {
        try {
            if (!this.f21300a.contains(Constants.ACCEPT_TIME_SEPARATOR_SERVER)) {
                this.f21303d = Integer.valueOf(this.f21300a).intValue();
                this.f21304e = false;
                return;
            }
            String[] strArrSplit = this.f21300a.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            if (strArrSplit.length == 2) {
                this.f21301b = Integer.valueOf(strArrSplit[0]).intValue();
                int iIntValue = Integer.valueOf(strArrSplit[1]).intValue();
                this.f21302c = iIntValue;
                if (this.f21301b < 1) {
                    this.f21301b = 1;
                }
                if (iIntValue > 24) {
                    this.f21302c = 24;
                }
            }
            this.f21304e = true;
        } catch (Throwable unused) {
        }
    }

    public boolean a(int i2) {
        int i3;
        if (this.f21304e) {
            int i4 = this.f21301b;
            if (i4 != -1 && (i3 = this.f21302c) != -1 && i2 >= i4 && i2 <= i3) {
                return true;
            }
        } else {
            int i5 = this.f21303d;
            if (i5 != -1 && i2 == i5) {
                return true;
            }
        }
        return false;
    }
}

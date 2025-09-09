package com.alibaba.sdk.android.httpdns.b;

import android.text.TextUtils;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.text.SimpleDateFormat;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final SimpleDateFormat f8799a = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss);

    public static long a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            return Long.valueOf(str).longValue();
        } catch (Exception unused) {
            return 0L;
        }
    }

    static String c(String str) {
        try {
            return f8799a.format(Long.valueOf(a(str) * 1000));
        } catch (Exception unused) {
            return f8799a.format(Long.valueOf(System.currentTimeMillis()));
        }
    }

    static String d(String str) {
        try {
            return String.valueOf(f8799a.parse(str).getTime() / 1000);
        } catch (Exception unused) {
            return String.valueOf(System.currentTimeMillis() / 1000);
        }
    }
}

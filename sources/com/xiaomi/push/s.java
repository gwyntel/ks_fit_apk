package com.xiaomi.push;

import android.os.Looper;
import android.text.TextUtils;
import java.util.Collection;

/* loaded from: classes4.dex */
public class s {

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private final String f24412a;

        /* renamed from: a, reason: collision with other field name */
        private final StringBuilder f940a;

        /* renamed from: b, reason: collision with root package name */
        private final String f24413b;

        public a() {
            this(":", ",");
        }

        public a a(String str, Object obj) {
            if (!TextUtils.isEmpty(str)) {
                if (this.f940a.length() > 0) {
                    this.f940a.append(this.f24413b);
                }
                StringBuilder sb = this.f940a;
                sb.append(str);
                sb.append(this.f24412a);
                sb.append(obj);
            }
            return this;
        }

        public String toString() {
            return this.f940a.toString();
        }

        public a(String str, String str2) {
            this.f940a = new StringBuilder();
            this.f24412a = str;
            this.f24413b = str2;
        }
    }

    public static int a(String str, int i2) {
        if (TextUtils.isEmpty(str)) {
            return i2;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return i2;
        }
    }

    public static int b(String str, int i2) {
        return !TextUtils.isEmpty(str) ? ((str.hashCode() / 10) * 10) + i2 : i2;
    }

    public static long a(String str, long j2) {
        if (TextUtils.isEmpty(str)) {
            return j2;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception unused) {
            return j2;
        }
    }

    public static boolean a(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean a() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}

package com.xiaomi.push.service;

import android.text.TextUtils;

/* loaded from: classes4.dex */
public class bc {

    /* renamed from: a, reason: collision with root package name */
    private static long f24493a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static String f1029a = "";

    public static String a() {
        if (TextUtils.isEmpty(f1029a)) {
            f1029a = com.xiaomi.push.bp.a(4);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(f1029a);
        long j2 = f24493a;
        f24493a = 1 + j2;
        sb.append(j2);
        return sb.toString();
    }

    public static String b() {
        return com.xiaomi.push.bp.a(32);
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str) || str.length() < 32) {
            return str;
        }
        try {
            return "BlockId_" + str.substring(8);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d("Exception occurred when filtering registration packet id for log. " + e2);
            return "UnexpectedId";
        }
    }
}

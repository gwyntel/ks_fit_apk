package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.C0472r;
import com.xiaomi.push.iq;
import com.xiaomi.push.ir;
import com.xiaomi.push.ix;
import com.xiaomi.push.jm;
import com.xiaomi.push.jx;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public class ca {

    /* renamed from: a, reason: collision with root package name */
    private static String f24570a;

    /* renamed from: a, reason: collision with other field name */
    private static SimpleDateFormat f1069a;

    /* renamed from: a, reason: collision with other field name */
    private static AtomicLong f1070a = new AtomicLong(0);

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        f1069a = simpleDateFormat;
        f24570a = simpleDateFormat.format(Long.valueOf(System.currentTimeMillis()));
    }

    public static synchronized String a() {
        String str;
        try {
            str = f1069a.format(Long.valueOf(System.currentTimeMillis()));
            if (!TextUtils.equals(f24570a, str)) {
                f1070a.set(0L);
                f24570a = str;
            }
        } catch (Throwable th) {
            throw th;
        }
        return str + Constants.ACCEPT_TIME_SEPARATOR_SERVER + f1070a.incrementAndGet();
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList<com.xiaomi.push.jm> a(java.util.List<com.xiaomi.push.ir> r11, java.lang.String r12, java.lang.String r13, int r14) throws java.lang.NumberFormatException {
        /*
            r0 = 0
            if (r11 != 0) goto L9
            java.lang.String r11 = "requests can not be null in TinyDataHelper.transToThriftObj()."
            com.xiaomi.channel.commonutils.logger.b.d(r11)
            return r0
        L9:
            int r1 = r11.size()
            if (r1 != 0) goto L15
            java.lang.String r11 = "requests.length is 0 in TinyDataHelper.transToThriftObj()."
            com.xiaomi.channel.commonutils.logger.b.d(r11)
            return r0
        L15:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            com.xiaomi.push.iq r2 = new com.xiaomi.push.iq
            r2.<init>()
            r3 = 0
            r4 = r3
            r5 = r4
        L22:
            int r6 = r11.size()
            if (r4 >= r6) goto Lad
            java.lang.Object r6 = r11.get(r4)
            com.xiaomi.push.ir r6 = (com.xiaomi.push.ir) r6
            if (r6 != 0) goto L32
            goto La9
        L32:
            java.util.Map r7 = r6.m517a()
            if (r7 == 0) goto L71
            java.util.Map r7 = r6.m517a()
            java.lang.String r8 = "item_size"
            boolean r7 = r7.containsKey(r8)
            if (r7 == 0) goto L71
            java.util.Map r7 = r6.m517a()
            java.lang.Object r7 = r7.get(r8)
            java.lang.String r7 = (java.lang.String) r7
            boolean r9 = android.text.TextUtils.isEmpty(r7)
            if (r9 != 0) goto L59
            int r7 = java.lang.Integer.parseInt(r7)     // Catch: java.lang.Exception -> L59
            goto L5a
        L59:
            r7 = r3
        L5a:
            java.util.Map r9 = r6.m517a()
            int r9 = r9.size()
            r10 = 1
            if (r9 != r10) goto L69
            r6.a(r0)
            goto L72
        L69:
            java.util.Map r9 = r6.m517a()
            r9.remove(r8)
            goto L72
        L71:
            r7 = r3
        L72:
            if (r7 > 0) goto L79
            byte[] r7 = com.xiaomi.push.jx.a(r6)
            int r7 = r7.length
        L79:
            if (r7 <= r14) goto L94
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "TinyData is too big, ignore upload request item:"
            r7.append(r8)
            java.lang.String r6 = r6.d()
            r7.append(r6)
            java.lang.String r6 = r7.toString()
            com.xiaomi.channel.commonutils.logger.b.d(r6)
            goto La9
        L94:
            int r8 = r5 + r7
            if (r8 <= r14) goto La5
            com.xiaomi.push.jm r2 = a(r12, r13, r2)
            r1.add(r2)
            com.xiaomi.push.iq r2 = new com.xiaomi.push.iq
            r2.<init>()
            r5 = r3
        La5:
            r2.a(r6)
            int r5 = r5 + r7
        La9:
            int r4 = r4 + 1
            goto L22
        Lad:
            int r11 = r2.a()
            if (r11 == 0) goto Lba
            com.xiaomi.push.jm r11 = a(r12, r13, r2)
            r1.add(r11)
        Lba:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.ca.a(java.util.List, java.lang.String, java.lang.String, int):java.util.ArrayList");
    }

    private static jm a(String str, String str2, iq iqVar) {
        return new jm("-1", false).d(str).b(str2).a(com.xiaomi.push.x.a(jx.a(iqVar))).c(ix.UploadTinyData.f620a);
    }

    public static boolean a(ir irVar, boolean z2) {
        if (irVar == null) {
            com.xiaomi.channel.commonutils.logger.b.m91a("item is null, verfiy ClientUploadDataItem failed.");
            return true;
        }
        if (!z2 && TextUtils.isEmpty(irVar.f592a)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("item.channel is null or empty, verfiy ClientUploadDataItem failed.");
            return true;
        }
        if (TextUtils.isEmpty(irVar.f599d)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("item.category is null or empty, verfiy ClientUploadDataItem failed.");
            return true;
        }
        if (TextUtils.isEmpty(irVar.f598c)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("item.name is null or empty, verfiy ClientUploadDataItem failed.");
            return true;
        }
        if (!com.xiaomi.push.bp.m220a(irVar.f599d)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("item.category can only contain ascii char, verfiy ClientUploadDataItem failed.");
            return true;
        }
        if (!com.xiaomi.push.bp.m220a(irVar.f598c)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("item.name can only contain ascii char, verfiy ClientUploadDataItem failed.");
            return true;
        }
        String str = irVar.f597b;
        if (str == null || str.length() <= 30720) {
            return false;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("item.data is too large(" + irVar.f597b.length() + "), max size for data is 30720 , verfiy ClientUploadDataItem failed.");
        return true;
    }

    public static void a(Context context, String str, String str2, long j2, String str3) {
        ir irVar = new ir();
        irVar.d(str);
        irVar.c(str2);
        irVar.a(j2);
        irVar.b(str3);
        irVar.a("push_sdk_channel");
        irVar.g(context.getPackageName());
        irVar.e(context.getPackageName());
        irVar.a(true);
        irVar.b(System.currentTimeMillis());
        irVar.f(a());
        cb.a(context, irVar);
    }

    public static boolean a(String str) {
        return !C0472r.m688b() || Constants.HYBRID_PACKAGE_NAME.equals(str);
    }
}

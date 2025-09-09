package com.vivo.push.util;

import android.os.Build;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class j {

    /* renamed from: e, reason: collision with root package name */
    private static Method f23244e;

    /* renamed from: a, reason: collision with root package name */
    public static final boolean f23240a = b("rom_1.0");

    /* renamed from: b, reason: collision with root package name */
    public static final boolean f23241b = b("rom_2.0");

    /* renamed from: c, reason: collision with root package name */
    public static final boolean f23242c = b("rom_2.5");

    /* renamed from: d, reason: collision with root package name */
    public static final boolean f23243d = b("rom_3.0");

    /* renamed from: f, reason: collision with root package name */
    private static String f23245f = null;

    /* renamed from: g, reason: collision with root package name */
    private static String f23246g = null;

    public static String a(String str, String str2) {
        String str3;
        try {
            str3 = (String) Class.forName("android.os.SystemProperties").getMethod(TmpConstant.PROPERTY_IDENTIFIER_GET, String.class).invoke(null, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            str3 = str2;
        }
        return (str3 == null || str3.length() == 0) ? str2 : str3;
    }

    private static boolean b(String str) {
        String strB = z.b("ro.vivo.rom", "");
        String strB2 = z.b("ro.vivo.rom.version", "");
        p.d("Device", "ro.vivo.rom = " + strB + " ; ro.vivo.rom.version = " + strB2);
        if (strB == null || !strB.contains(str)) {
            return strB2 != null && strB2.contains(str);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0085 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0087 A[Catch: all -> 0x0051, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0006, B:6:0x000b, B:8:0x000f, B:12:0x0053, B:13:0x005a, B:17:0x0087), top: B:25:0x0006, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized java.lang.String a() {
        /*
            r0 = 0
            r1 = 2
            r2 = 1
            java.lang.Class<com.vivo.push.util.j> r3 = com.vivo.push.util.j.class
            monitor-enter(r3)
            java.lang.String r4 = com.vivo.push.util.j.f23245f     // Catch: java.lang.Throwable -> L51
            r5 = 0
            if (r4 != 0) goto L5a
            java.lang.String r4 = com.vivo.push.util.j.f23246g     // Catch: java.lang.Throwable -> L51
            if (r4 != 0) goto L5a
            java.lang.String r4 = "android.os.SystemProperties"
            java.lang.Class r4 = java.lang.Class.forName(r4)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r6 = "get"
            java.lang.Class[] r7 = new java.lang.Class[r1]     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            r7[r0] = r8     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            r7[r2] = r8     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.reflect.Method r4 = r4.getDeclaredMethod(r6, r7)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            com.vivo.push.util.j.f23244e = r4     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            r4.setAccessible(r2)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.reflect.Method r4 = com.vivo.push.util.j.f23244e     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.Object[] r6 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r7 = "ro.vivo.rom"
            r6[r0] = r7     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r7 = "@><@"
            r6[r2] = r7     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.Object r4 = r4.invoke(r5, r6)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            com.vivo.push.util.j.f23245f = r4     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.reflect.Method r4 = com.vivo.push.util.j.f23244e     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r6 = "ro.vivo.rom.version"
            r1[r0] = r6     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r0 = "@><@"
            r1[r2] = r0     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.Object r0 = r4.invoke(r5, r1)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            java.lang.String r0 = (java.lang.String) r0     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            com.vivo.push.util.j.f23246g = r0     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            goto L5a
        L51:
            r0 = move-exception
            goto L97
        L53:
            java.lang.String r0 = "Device"
            java.lang.String r1 = "getRomCode error"
            com.vivo.push.util.p.b(r0, r1)     // Catch: java.lang.Throwable -> L51
        L5a:
            java.lang.String r0 = "Device"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L51
            java.lang.String r2 = "sRomProperty1 : "
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L51
            java.lang.String r2 = com.vivo.push.util.j.f23245f     // Catch: java.lang.Throwable -> L51
            r1.append(r2)     // Catch: java.lang.Throwable -> L51
            java.lang.String r2 = " ; sRomProperty2 : "
            r1.append(r2)     // Catch: java.lang.Throwable -> L51
            java.lang.String r2 = com.vivo.push.util.j.f23246g     // Catch: java.lang.Throwable -> L51
            r1.append(r2)     // Catch: java.lang.Throwable -> L51
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L51
            com.vivo.push.util.p.d(r0, r1)     // Catch: java.lang.Throwable -> L51
            java.lang.String r0 = com.vivo.push.util.j.f23245f     // Catch: java.lang.Throwable -> L51
            java.lang.String r0 = a(r0)     // Catch: java.lang.Throwable -> L51
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L51
            if (r1 != 0) goto L87
            monitor-exit(r3)
            return r0
        L87:
            java.lang.String r0 = com.vivo.push.util.j.f23246g     // Catch: java.lang.Throwable -> L51
            java.lang.String r0 = a(r0)     // Catch: java.lang.Throwable -> L51
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L51
            if (r1 != 0) goto L95
            monitor-exit(r3)
            return r0
        L95:
            monitor-exit(r3)
            return r5
        L97:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L51
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.vivo.push.util.j.a():java.lang.String");
    }

    public static boolean b() {
        String str = Build.MANUFACTURER;
        if (TextUtils.isEmpty(str)) {
            p.d("Device", "Build.MANUFACTURER is null");
            return false;
        }
        p.d("Device", "Build.MANUFACTURER is " + str);
        return str.toLowerCase().contains("bbk") || str.toLowerCase().startsWith("vivo");
    }

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Matcher matcher = Pattern.compile("rom_([\\d]*).?([\\d]*)", 2).matcher(str);
        if (!matcher.find()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(matcher.group(1));
        sb.append(TextUtils.isEmpty(matcher.group(2)) ? "0" : matcher.group(2).substring(0, 1));
        return sb.toString();
    }
}

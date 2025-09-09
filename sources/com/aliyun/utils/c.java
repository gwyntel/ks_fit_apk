package com.aliyun.utils;

import android.os.Build;
import android.os.Process;
import com.cicada.player.utils.Logger;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static String f12124a = "c";

    /* renamed from: b, reason: collision with root package name */
    private int f12125b;

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            c.this.f();
        }
    }

    class b implements FileFilter {
        b() {
        }

        @Override // java.io.FileFilter
        public boolean accept(File file) {
            return Pattern.matches("cpu[0-9]", file.getName());
        }
    }

    /* renamed from: com.aliyun.utils.c$c, reason: collision with other inner class name */
    class C0101c implements e {

        /* renamed from: a, reason: collision with root package name */
        int f12127a = -1;

        C0101c() {
        }

        @Override // com.aliyun.utils.c.e
        public void a(String str) {
            LinkedList linkedListB;
            if (!str.contains(Process.myPid() + "") || (linkedListB = c.b(str.split(" "))) == null) {
                return;
            }
            if (this.f12127a < 0) {
                int i2 = 0;
                while (true) {
                    if (i2 >= linkedListB.size()) {
                        break;
                    }
                    if (((String) linkedListB.get(i2)).contains("%")) {
                        this.f12127a = i2;
                        break;
                    }
                    i2++;
                }
            }
            int i3 = this.f12127a;
            if (i3 < 0) {
                Logger.v(c.f12124a, "getCpuUsageBefore26 unknow ");
                return;
            }
            String strSubstring = (String) linkedListB.get(i3);
            if (strSubstring.contains("%")) {
                strSubstring = strSubstring.substring(0, strSubstring.indexOf("%"));
            }
            try {
                c.this.f12125b = (int) Float.parseFloat(strSubstring);
                Logger.v(c.f12124a, "getCpuUsageBefore26 mMyPidPercent update " + c.this.f12125b);
            } catch (Exception unused) {
            }
        }
    }

    class d implements e {
        d() {
        }

        @Override // com.aliyun.utils.c.e
        public void a(String str) throws NumberFormatException {
            try {
                c.this.f12125b = (int) (Float.parseFloat(str) / c.b());
                Logger.v(c.f12124a, "getCpuUsageAfter25 mMyPidPercent update " + c.this.f12125b);
            } catch (Exception unused) {
            }
        }
    }

    private interface e {
        void a(String str);
    }

    c() {
        new Thread(new a()).start();
    }

    public static int b() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new b()).length;
        } catch (Exception unused) {
            return 1;
        }
    }

    private void c() throws Throwable {
        a("top -p " + Process.myPid() + " -o %CPU", new d());
    }

    private void d() throws Throwable {
        a(ViewHierarchyConstants.DIMENSION_TOP_KEY, new C0101c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() throws Throwable {
        if (Build.VERSION.SDK_INT > 25) {
            c();
        } else {
            d();
        }
    }

    int e() {
        Logger.d(f12124a, "getMyPicCpuPercent = " + this.f12125b);
        return this.f12125b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static LinkedList<String> b(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        LinkedList<String> linkedList = new LinkedList<>();
        int length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (!strArr[i2].trim().equals("")) {
                linkedList.add(strArr[i2]);
            }
        }
        return linkedList;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0045 A[Catch: all -> 0x004d, TryCatch #7 {all -> 0x004d, blocks: (B:27:0x0040, B:29:0x0045, B:31:0x004a), top: B:46:0x0040 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x004a A[Catch: all -> 0x004d, TRY_LEAVE, TryCatch #7 {all -> 0x004d, blocks: (B:27:0x0040, B:29:0x0045, B:31:0x004a), top: B:46:0x0040 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0052 A[Catch: all -> 0x005d, TRY_ENTER, TryCatch #8 {all -> 0x005d, blocks: (B:13:0x0025, B:14:0x002b, B:36:0x0052, B:38:0x0057), top: B:48:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0057 A[Catch: all -> 0x005d, TRY_LEAVE, TryCatch #8 {all -> 0x005d, blocks: (B:13:0x0025, B:14:0x002b, B:36:0x0052, B:38:0x0057), top: B:48:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x005d A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0040 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r4, com.aliyun.utils.c.e r5) throws java.lang.Throwable {
        /*
            r3 = this;
            r0 = 0
            java.lang.Runtime r1 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L4e
            java.lang.Process r4 = r1.exec(r4)     // Catch: java.lang.Throwable -> L3a java.lang.Exception -> L4e
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L4f
            java.io.InputStream r2 = r4.getInputStream()     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L4f
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L38 java.lang.Exception -> L4f
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
        L17:
            java.lang.String r0 = r2.readLine()     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L31
            if (r0 == 0) goto L25
            if (r5 == 0) goto L17
            r5.a(r0)     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L31
            goto L17
        L23:
            r5 = move-exception
            goto L2f
        L25:
            r1.close()     // Catch: java.lang.Throwable -> L5d
            r2.close()     // Catch: java.lang.Throwable -> L5d
        L2b:
            r4.destroy()     // Catch: java.lang.Throwable -> L5d
            goto L5d
        L2f:
            r0 = r1
            goto L3e
        L31:
            r0 = r1
            goto L50
        L33:
            r5 = move-exception
            r2 = r0
            goto L2f
        L36:
            r2 = r0
            goto L31
        L38:
            r5 = move-exception
            goto L3d
        L3a:
            r4 = move-exception
            r5 = r4
            r4 = r0
        L3d:
            r2 = r0
        L3e:
            if (r0 == 0) goto L43
            r0.close()     // Catch: java.lang.Throwable -> L4d
        L43:
            if (r2 == 0) goto L48
            r2.close()     // Catch: java.lang.Throwable -> L4d
        L48:
            if (r4 == 0) goto L4d
            r4.destroy()     // Catch: java.lang.Throwable -> L4d
        L4d:
            throw r5
        L4e:
            r4 = r0
        L4f:
            r2 = r0
        L50:
            if (r0 == 0) goto L55
            r0.close()     // Catch: java.lang.Throwable -> L5d
        L55:
            if (r2 == 0) goto L5a
            r2.close()     // Catch: java.lang.Throwable -> L5d
        L5a:
            if (r4 == 0) goto L5d
            goto L2b
        L5d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.c.a(java.lang.String, com.aliyun.utils.c$e):void");
    }
}

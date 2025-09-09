package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;

/* loaded from: classes4.dex */
public class ij {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f23987a = false;

    static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Context f23988a;

        /* renamed from: a, reason: collision with other field name */
        private im f579a;

        public a(Context context, im imVar) {
            this.f579a = imVar;
            this.f23988a = context;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            ij.c(this.f23988a, this.f579a);
        }
    }

    public static void a(Context context, im imVar) {
        ah.a(context).a(new a(context, imVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void c(android.content.Context r11, com.xiaomi.push.im r12) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "/"
            java.lang.String r1 = "/tdReadTemp"
            boolean r2 = com.xiaomi.push.ij.f23987a
            if (r2 != 0) goto Ldf
            r2 = 1
            com.xiaomi.push.ij.f23987a = r2
            java.io.File r2 = new java.io.File
            java.io.File r3 = r11.getFilesDir()
            java.lang.String r4 = "tiny_data.data"
            r2.<init>(r3, r4)
            boolean r3 = r2.exists()
            java.lang.String r5 = "TinyData no ready file to get data."
            if (r3 != 0) goto L22
            com.xiaomi.channel.commonutils.logger.b.m91a(r5)
            return
        L22:
            a(r11)
            byte[] r3 = com.xiaomi.push.service.cb.a(r11)
            r6 = 0
            java.io.File r7 = new java.io.File     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L83
            java.io.File r8 = r11.getFilesDir()     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L83
            java.lang.String r9 = "tiny_data.lock"
            r7.<init>(r8, r9)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L83
            com.xiaomi.push.x.m809a(r7)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L83
            java.io.RandomAccessFile r8 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L83
            java.lang.String r9 = "rw"
            r8.<init>(r7, r9)     // Catch: java.lang.Throwable -> L80 java.lang.Exception -> L83
            java.nio.channels.FileChannel r7 = r8.getChannel()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.nio.channels.FileLock r6 = r7.lock()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.io.File r7 = new java.io.File     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r9.<init>()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.io.File r10 = r11.getFilesDir()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r9.append(r10)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r9.append(r1)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r9.append(r0)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r9.append(r4)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r7.<init>(r9)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            r2.renameTo(r7)     // Catch: java.lang.Throwable -> L7c java.lang.Exception -> L7e
            if (r6 == 0) goto L78
            boolean r2 = r6.isValid()
            if (r2 == 0) goto L78
            r6.release()     // Catch: java.io.IOException -> L74
            goto L78
        L74:
            r2 = move-exception
        L75:
            com.xiaomi.channel.commonutils.logger.b.a(r2)
        L78:
            com.xiaomi.push.x.a(r8)
            goto L96
        L7c:
            r11 = move-exception
            goto Lcb
        L7e:
            r2 = move-exception
            goto L85
        L80:
            r11 = move-exception
            r8 = r6
            goto Lcb
        L83:
            r2 = move-exception
            r8 = r6
        L85:
            com.xiaomi.channel.commonutils.logger.b.a(r2)     // Catch: java.lang.Throwable -> L7c
            if (r6 == 0) goto L78
            boolean r2 = r6.isValid()
            if (r2 == 0) goto L78
            r6.release()     // Catch: java.io.IOException -> L94
            goto L78
        L94:
            r2 = move-exception
            goto L75
        L96:
            java.io.File r2 = new java.io.File
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.io.File r7 = r11.getFilesDir()
            r6.append(r7)
            r6.append(r1)
            r6.append(r0)
            r6.append(r4)
            java.lang.String r0 = r6.toString()
            r2.<init>(r0)
            boolean r0 = r2.exists()
            if (r0 != 0) goto Lbe
            com.xiaomi.channel.commonutils.logger.b.m91a(r5)
            return
        Lbe:
            a(r11, r12, r2, r3)
            r12 = 0
            com.xiaomi.push.ii.a(r12)
            b(r11)
            com.xiaomi.push.ij.f23987a = r12
            return
        Lcb:
            if (r6 == 0) goto Ldb
            boolean r12 = r6.isValid()
            if (r12 == 0) goto Ldb
            r6.release()     // Catch: java.io.IOException -> Ld7
            goto Ldb
        Ld7:
            r12 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r12)
        Ldb:
            com.xiaomi.push.x.a(r8)
            throw r11
        Ldf:
            java.lang.String r11 = "TinyData extractTinyData is running"
            com.xiaomi.channel.commonutils.logger.b.m91a(r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ij.c(android.content.Context, com.xiaomi.push.im):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a8, code lost:
    
        com.xiaomi.channel.commonutils.logger.b.d("TinyData read from cache file failed cause lengthBuffer < 1 || too big. length:" + r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(android.content.Context r12, com.xiaomi.push.im r13, java.io.File r14, byte[] r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ij.a(android.content.Context, com.xiaomi.push.im, java.io.File, byte[]):void");
    }

    private static void b(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 4).edit();
        editorEdit.putLong("last_tiny_data_upload_timestamp", System.currentTimeMillis() / 1000);
        editorEdit.commit();
    }

    private static void a(Context context) {
        File file = new File(context.getFilesDir() + "/tdReadTemp");
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }
}

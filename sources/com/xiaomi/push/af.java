package com.xiaomi.push;

import android.content.Context;
import com.luck.picture.lib.permissions.PermissionConfig;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/* loaded from: classes4.dex */
public class af {
    public static boolean a(Context context, String str, long j2) throws Throwable {
        RandomAccessFile randomAccessFile;
        if (!g.d(context, PermissionConfig.WRITE_EXTERNAL_STORAGE)) {
            return true;
        }
        FileLock fileLockLock = null;
        try {
            try {
                File file = new File(new File(context.getExternalFilesDir(null), "/.vdevdir/"), "lcfp.lock");
                x.m809a(file);
                randomAccessFile = new RandomAccessFile(file, "rw");
            } catch (IOException e2) {
                e = e2;
                randomAccessFile = null;
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    try {
                        fileLockLock.release();
                    } catch (IOException unused) {
                    }
                }
                x.a((Closeable) null);
                throw th;
            }
            try {
                fileLockLock = randomAccessFile.getChannel().lock();
                boolean zB = b(context, str, j2);
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException unused2) {
                    }
                }
                x.a(randomAccessFile);
                return zB;
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException unused3) {
                    }
                }
                x.a(randomAccessFile);
                return true;
            }
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0 && fileLockLock.isValid()) {
                fileLockLock.release();
            }
            x.a((Closeable) null);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d9 A[Catch: all -> 0x00e9, IOException -> 0x00eb, LOOP:0: B:40:0x00d3->B:42:0x00d9, LOOP_END, TRY_LEAVE, TryCatch #3 {all -> 0x00e9, blocks: (B:39:0x00cf, B:40:0x00d3, B:42:0x00d9, B:53:0x00f6), top: B:60:0x00c5 }] */
    /* JADX WARN: Type inference failed for: r10v0, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean b(android.content.Context r16, java.lang.String r17, long r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.af.b(android.content.Context, java.lang.String, long):boolean");
    }
}

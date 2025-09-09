package com.ta.a.c;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static File f20016a;

    /* renamed from: a, reason: collision with other field name */
    private static FileChannel f59a;

    /* renamed from: a, reason: collision with other field name */
    private static FileLock f60a;

    /* renamed from: b, reason: collision with root package name */
    private static File f20017b;

    public static synchronized void c() {
        try {
            f.e();
            if (f20016a == null) {
                f20016a = new File(com.ta.a.b.e.c());
            }
            if (!f20016a.exists()) {
                try {
                    f20016a.createNewFile();
                } catch (Exception unused) {
                    return;
                }
            }
            if (f59a == null) {
                try {
                    f59a = new RandomAccessFile(f20016a, "rw").getChannel();
                } catch (Exception unused2) {
                    return;
                }
            }
            try {
                f60a = f59a.lock();
            } catch (Throwable unused3) {
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized void d() {
        try {
            f.e();
            FileLock fileLock = f60a;
            if (fileLock != null) {
                try {
                    fileLock.release();
                } catch (Exception unused) {
                } catch (Throwable th) {
                    f60a = null;
                    throw th;
                }
                f60a = null;
            }
            FileChannel fileChannel = f59a;
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (Exception unused2) {
                } catch (Throwable th2) {
                    f59a = null;
                    throw th2;
                }
                f59a = null;
            }
        } catch (Throwable th3) {
            throw th3;
        }
    }
}

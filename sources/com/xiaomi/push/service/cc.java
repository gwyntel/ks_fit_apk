package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.ir;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/* loaded from: classes4.dex */
class cc implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24572a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ ir f1071a;

    cc(Context context, ir irVar) {
        this.f24572a = context;
        this.f1071a = irVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.IOException, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.IOException, java.lang.Throwable] */
    @Override // java.lang.Runnable
    public void run() {
        RandomAccessFile randomAccessFile;
        synchronized (cb.f24571a) {
            FileLock fileLock = null;
            fileLockLock = null;
            FileLock fileLockLock = null;
            fileLock = null;
            try {
                try {
                    File file = new File(this.f24572a.getFilesDir(), "tiny_data.lock");
                    com.xiaomi.push.x.m809a(file);
                    randomAccessFile = new RandomAccessFile(file, "rw");
                } catch (Exception e2) {
                    e = e2;
                    randomAccessFile = null;
                } catch (Throwable th) {
                    th = th;
                    randomAccessFile = null;
                    if (fileLock != null) {
                        try {
                            fileLock.release();
                        } catch (IOException e3) {
                            com.xiaomi.channel.commonutils.logger.b.a(e3);
                        }
                    }
                    com.xiaomi.push.x.a(randomAccessFile);
                    throw th;
                }
                try {
                    fileLockLock = randomAccessFile.getChannel().lock();
                    cb.c(this.f24572a, this.f1071a);
                    fileLock = fileLockLock;
                    if (fileLockLock != null) {
                        boolean zIsValid = fileLockLock.isValid();
                        fileLock = fileLockLock;
                        if (zIsValid) {
                            try {
                                fileLockLock.release();
                                fileLock = fileLockLock;
                            } catch (IOException e4) {
                                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e4);
                                fileLock = e4;
                            }
                        }
                    }
                } catch (Exception e5) {
                    e = e5;
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                    fileLock = fileLockLock;
                    if (fileLockLock != null) {
                        boolean zIsValid2 = fileLockLock.isValid();
                        fileLock = fileLockLock;
                        if (zIsValid2) {
                            try {
                                fileLockLock.release();
                                fileLock = fileLockLock;
                            } catch (IOException e6) {
                                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e6);
                                fileLock = e6;
                            }
                        }
                    }
                    com.xiaomi.push.x.a(randomAccessFile);
                }
                com.xiaomi.push.x.a(randomAccessFile);
            } catch (Throwable th2) {
                th = th2;
                if (fileLock != null && fileLock.isValid()) {
                    fileLock.release();
                }
                com.xiaomi.push.x.a(randomAccessFile);
                throw th;
            }
        }
    }
}

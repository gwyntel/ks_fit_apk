package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static volatile a f24442a;

    /* renamed from: a, reason: collision with other field name */
    private Context f984a;

    /* renamed from: e, reason: collision with root package name */
    private volatile String f24446e;

    /* renamed from: f, reason: collision with root package name */
    private volatile String f24447f;

    /* renamed from: a, reason: collision with other field name */
    private final Object f985a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private final Object f24443b = new Object();

    /* renamed from: a, reason: collision with other field name */
    private final String f986a = "mipush_region";

    /* renamed from: b, reason: collision with other field name */
    private final String f987b = "mipush_country_code";

    /* renamed from: c, reason: collision with root package name */
    private final String f24444c = "mipush_region.lock";

    /* renamed from: d, reason: collision with root package name */
    private final String f24445d = "mipush_country_code.lock";

    public a(Context context) {
        this.f984a = context;
    }

    public static a a(Context context) {
        if (f24442a == null) {
            synchronized (a.class) {
                try {
                    if (f24442a == null) {
                        f24442a = new a(context);
                    }
                } finally {
                }
            }
        }
        return f24442a;
    }

    public String b() {
        if (TextUtils.isEmpty(this.f24447f)) {
            this.f24447f = a(this.f984a, "mipush_country_code", "mipush_country_code.lock", this.f24443b);
        }
        return this.f24447f;
    }

    public void b(String str, boolean z2) {
        if (!TextUtils.equals(str, this.f24447f)) {
            this.f24447f = str;
        }
        if (z2) {
            a(this.f984a, str, "mipush_country_code", "mipush_region.lock", this.f985a);
        }
    }

    public String a() {
        if (TextUtils.isEmpty(this.f24446e)) {
            this.f24446e = a(this.f984a, "mipush_region", "mipush_region.lock", this.f985a);
        }
        return this.f24446e;
    }

    public void a(String str, boolean z2) {
        if (!TextUtils.equals(str, this.f24446e)) {
            this.f24446e = str;
        }
        if (z2) {
            a(this.f984a, str, "mipush_region", "mipush_region.lock", this.f985a);
        }
    }

    private void a(Context context, String str, String str2, String str3, Object obj) {
        RandomAccessFile randomAccessFile;
        synchronized (obj) {
            FileLock fileLockLock = null;
            try {
                File file = new File(context.getFilesDir(), str3);
                com.xiaomi.push.x.m809a(file);
                randomAccessFile = new RandomAccessFile(file, "rw");
            } catch (Exception e2) {
                e = e2;
                randomAccessFile = null;
            } catch (Throwable th) {
                th = th;
                randomAccessFile = null;
                if (fileLockLock != null) {
                    try {
                        fileLockLock.release();
                    } catch (IOException e3) {
                        com.xiaomi.channel.commonutils.logger.b.a(e3);
                    }
                }
                com.xiaomi.push.x.a(randomAccessFile);
                throw th;
            }
            try {
                try {
                    fileLockLock = randomAccessFile.getChannel().lock();
                    com.xiaomi.push.x.a(new File(context.getFilesDir(), str2), str);
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e4) {
                            com.xiaomi.channel.commonutils.logger.b.a(e4);
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        fileLockLock.release();
                    }
                    com.xiaomi.push.x.a(randomAccessFile);
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                com.xiaomi.channel.commonutils.logger.b.a(e);
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException e6) {
                        com.xiaomi.channel.commonutils.logger.b.a(e6);
                    }
                }
                com.xiaomi.push.x.a(randomAccessFile);
            }
            com.xiaomi.push.x.a(randomAccessFile);
        }
    }

    private String a(Context context, String str, String str2, Object obj) {
        RandomAccessFile randomAccessFile;
        FileLock fileLockLock;
        File file = new File(context.getFilesDir(), str);
        FileLock fileLock = null;
        if (!file.exists()) {
            com.xiaomi.channel.commonutils.logger.b.m91a("No ready file to get data from " + str);
            return null;
        }
        synchronized (obj) {
            try {
                File file2 = new File(context.getFilesDir(), str2);
                com.xiaomi.push.x.m809a(file2);
                randomAccessFile = new RandomAccessFile(file2, "rw");
            } catch (Exception e2) {
                e = e2;
                randomAccessFile = null;
                fileLockLock = null;
            } catch (Throwable th) {
                th = th;
                randomAccessFile = null;
            }
            try {
                fileLockLock = randomAccessFile.getChannel().lock();
            } catch (Exception e3) {
                e = e3;
                fileLockLock = null;
            } catch (Throwable th2) {
                th = th2;
                if (fileLock != null) {
                    try {
                        fileLock.release();
                    } catch (IOException e4) {
                        com.xiaomi.channel.commonutils.logger.b.a(e4);
                    }
                }
                com.xiaomi.push.x.a(randomAccessFile);
                throw th;
            }
            try {
                try {
                    String strA = com.xiaomi.push.x.a(file);
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e5) {
                            com.xiaomi.channel.commonutils.logger.b.a(e5);
                        }
                    }
                    com.xiaomi.push.x.a(randomAccessFile);
                    return strA;
                } catch (Throwable th3) {
                    th = th3;
                    fileLock = fileLockLock;
                    if (fileLock != null && fileLock.isValid()) {
                        fileLock.release();
                    }
                    com.xiaomi.push.x.a(randomAccessFile);
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                com.xiaomi.channel.commonutils.logger.b.a(e);
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException e7) {
                        com.xiaomi.channel.commonutils.logger.b.a(e7);
                    }
                }
                com.xiaomi.push.x.a(randomAccessFile);
                return null;
            }
        }
    }
}

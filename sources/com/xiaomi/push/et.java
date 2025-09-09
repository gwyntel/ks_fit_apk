package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.push.ah;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import org.joda.time.DateTimeConstants;

/* loaded from: classes4.dex */
public abstract class et extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    protected int f23669a;

    /* renamed from: a, reason: collision with other field name */
    protected Context f338a;

    public et(Context context, int i2) {
        this.f23669a = i2;
        this.f338a = context;
    }

    private String d() {
        return "dc_job_result_" + mo224a();
    }

    public abstract ip a();

    /* renamed from: a, reason: collision with other method in class */
    protected boolean m314a() {
        return ep.a(this.f338a, String.valueOf(mo224a()), this.f23669a);
    }

    public abstract String b();

    /* renamed from: b, reason: collision with other method in class */
    protected boolean m315b() {
        return true;
    }

    /* renamed from: c, reason: collision with other method in class */
    protected boolean m316c() {
        return false;
    }

    @Override // java.lang.Runnable
    public void run() {
        String strB = b();
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        if (m314a()) {
            com.xiaomi.channel.commonutils.logger.b.m91a("DC run job mutual: " + mo224a());
            return;
        }
        em emVarM312a = en.a().m312a();
        String strA = emVarM312a == null ? "" : emVarM312a.a();
        if (!TextUtils.isEmpty(strA) && m315b()) {
            if (m316c()) {
                SharedPreferences sharedPreferences = this.f338a.getSharedPreferences("mipush_extra", 0);
                if (bp.a(strB).equals(sharedPreferences.getString(d(), null))) {
                    long j2 = sharedPreferences.getLong(c(), 0L);
                    int iA = com.xiaomi.push.service.az.a(this.f338a).a(is.DCJobUploadRepeatedInterval.a(), DateTimeConstants.SECONDS_PER_WEEK);
                    if ((System.currentTimeMillis() - j2) / 1000 < this.f23669a) {
                        return;
                    }
                    if ((System.currentTimeMillis() - j2) / 1000 < iA) {
                        strB = "same_" + j2;
                    }
                }
            }
            iv ivVar = new iv();
            ivVar.a(strB);
            ivVar.a(System.currentTimeMillis());
            ivVar.a(a());
            a(this.f338a, ivVar, strA);
        }
    }

    private String c() {
        return "dc_job_result_time_" + mo224a();
    }

    public static void a(Context context, iv ivVar) {
        em emVarM312a = en.a().m312a();
        String strA = emVarM312a == null ? "" : emVarM312a.a();
        if (TextUtils.isEmpty(strA) || TextUtils.isEmpty(ivVar.a())) {
            return;
        }
        a(context, ivVar, strA);
    }

    private static void a(Context context, iv ivVar, String str) {
        BufferedOutputStream bufferedOutputStream;
        RandomAccessFile randomAccessFile;
        byte[] bArrB = ep.b(str, jx.a(ivVar));
        if (bArrB == null || bArrB.length == 0) {
            return;
        }
        synchronized (eq.f23665a) {
            FileLock fileLock = null;
            try {
                File file = new File(context.getExternalFilesDir(null), "push_cdata.lock");
                x.m809a(file);
                randomAccessFile = new RandomAccessFile(file, "rw");
                try {
                    FileLock fileLockLock = randomAccessFile.getChannel().lock();
                    try {
                        File file2 = new File(context.getExternalFilesDir(null), "push_cdata.data");
                        bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2, true));
                        try {
                            bufferedOutputStream.write(ab.a(bArrB.length));
                            bufferedOutputStream.write(bArrB);
                            bufferedOutputStream.flush();
                            file2.setLastModified(0L);
                            if (fileLockLock != null && fileLockLock.isValid()) {
                                try {
                                    fileLockLock.release();
                                } catch (IOException unused) {
                                }
                            }
                            x.a(bufferedOutputStream);
                        } catch (IOException e2) {
                            e = e2;
                            fileLock = fileLockLock;
                            try {
                                e.printStackTrace();
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException unused2) {
                                    }
                                }
                                x.a(bufferedOutputStream);
                                x.a(randomAccessFile);
                            } catch (Throwable th) {
                                th = th;
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException unused3) {
                                    }
                                }
                                x.a(bufferedOutputStream);
                                x.a(randomAccessFile);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fileLock = fileLockLock;
                            if (fileLock != null) {
                                fileLock.release();
                            }
                            x.a(bufferedOutputStream);
                            x.a(randomAccessFile);
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        bufferedOutputStream = null;
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedOutputStream = null;
                    }
                } catch (IOException e4) {
                    e = e4;
                    bufferedOutputStream = null;
                } catch (Throwable th4) {
                    th = th4;
                    bufferedOutputStream = null;
                }
            } catch (IOException e5) {
                e = e5;
                bufferedOutputStream = null;
                randomAccessFile = null;
            } catch (Throwable th5) {
                th = th5;
                bufferedOutputStream = null;
                randomAccessFile = null;
            }
            x.a(randomAccessFile);
        }
    }
}

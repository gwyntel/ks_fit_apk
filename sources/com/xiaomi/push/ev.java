package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.push.ah;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ev extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    private Context f23670a;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f339a;

    /* renamed from: a, reason: collision with other field name */
    private com.xiaomi.push.service.az f340a;

    public ev(Context context) {
        this.f23670a = context;
        this.f339a = context.getSharedPreferences("mipush_extra", 0);
        this.f340a = com.xiaomi.push.service.az.a(context);
    }

    private boolean b() {
        if (!this.f340a.a(is.Upload3GSwitch.a(), true)) {
            return false;
        }
        return Math.abs((System.currentTimeMillis() / 1000) - this.f339a.getLong("last_upload_data_timestamp", -1L)) > ((long) Math.max(86400, this.f340a.a(is.Upload3GFrequency.a(), 432000)));
    }

    private boolean c() {
        if (!this.f340a.a(is.Upload4GSwitch.a(), true)) {
            return false;
        }
        return Math.abs((System.currentTimeMillis() / 1000) - this.f339a.getLong("last_upload_data_timestamp", -1L)) > ((long) Math.max(86400, this.f340a.a(is.Upload4GFrequency.a(), 259200)));
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return "1";
    }

    @Override // java.lang.Runnable
    public void run() throws IOException {
        File file = new File(this.f23670a.getExternalFilesDir(null), "push_cdata.data");
        if (!bg.d(this.f23670a)) {
            if (file.length() > 1863680) {
                file.delete();
                return;
            }
            return;
        }
        if (!m317a() && file.exists()) {
            List<iv> listA = a(file);
            if (!ac.a(listA)) {
                int size = listA.size();
                if (size > 4000) {
                    listA = listA.subList(size - 4000, size);
                }
                jg jgVar = new jg();
                jgVar.a(listA);
                byte[] bArrA = x.a(jx.a(jgVar));
                jm jmVar = new jm("-1", false);
                jmVar.c(ix.DataCollection.f620a);
                jmVar.a(bArrA);
                em emVarM312a = en.a().m312a();
                if (emVarM312a != null) {
                    emVarM312a.a(jmVar, in.Notification, null);
                }
                a();
            }
            file.delete();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m317a() {
        if (bg.e(this.f23670a)) {
            return false;
        }
        if ((bg.g(this.f23670a) || bg.f(this.f23670a)) && !c()) {
            return true;
        }
        return (bg.h(this.f23670a) && !b()) || bg.i(this.f23670a);
    }

    private void a() {
        SharedPreferences.Editor editorEdit = this.f339a.edit();
        editorEdit.putLong("last_upload_data_timestamp", System.currentTimeMillis() / 1000);
        editorEdit.commit();
    }

    private List<iv> a(File file) {
        RandomAccessFile randomAccessFile;
        FileInputStream fileInputStream;
        em emVarM312a = en.a().m312a();
        String strA = emVarM312a == null ? "" : emVarM312a.a();
        FileLock fileLock = null;
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        byte[] bArr = new byte[4];
        synchronized (eq.f23665a) {
            try {
                File file2 = new File(this.f23670a.getExternalFilesDir(null), "push_cdata.lock");
                x.m809a(file2);
                randomAccessFile = new RandomAccessFile(file2, "rw");
                try {
                    FileLock fileLockLock = randomAccessFile.getChannel().lock();
                    try {
                        fileInputStream = new FileInputStream(file);
                        while (fileInputStream.read(bArr) == 4) {
                            try {
                                int iA = ab.a(bArr);
                                byte[] bArr2 = new byte[iA];
                                if (fileInputStream.read(bArr2) != iA) {
                                    break;
                                }
                                byte[] bArrA = ep.a(strA, bArr2);
                                if (bArrA != null && bArrA.length != 0) {
                                    iv ivVar = new iv();
                                    jx.a(ivVar, bArrA);
                                    arrayList.add(ivVar);
                                    a(ivVar);
                                }
                            } catch (Exception unused) {
                                fileLock = fileLockLock;
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException unused2) {
                                    }
                                }
                                x.a((Closeable) fileInputStream);
                                x.a(randomAccessFile);
                                return arrayList;
                            } catch (Throwable th) {
                                th = th;
                                fileLock = fileLockLock;
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException unused3) {
                                    }
                                }
                                x.a((Closeable) fileInputStream);
                                x.a(randomAccessFile);
                                throw th;
                            }
                        }
                        if (fileLockLock != null && fileLockLock.isValid()) {
                            try {
                                fileLockLock.release();
                            } catch (IOException unused4) {
                            }
                        }
                        x.a((Closeable) fileInputStream);
                    } catch (Exception unused5) {
                        fileInputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = null;
                    }
                } catch (Exception unused6) {
                    fileInputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    fileInputStream = null;
                }
            } catch (Exception unused7) {
                randomAccessFile = null;
                fileInputStream = null;
            } catch (Throwable th4) {
                th = th4;
                randomAccessFile = null;
                fileInputStream = null;
            }
            x.a(randomAccessFile);
        }
        return arrayList;
    }

    private void a(iv ivVar) {
        if (ivVar.f611a != ip.AppInstallList || ivVar.f612a.startsWith("same_")) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.f339a.edit();
        editorEdit.putLong("dc_job_result_time_4", ivVar.f610a);
        editorEdit.putString("dc_job_result_4", bp.a(ivVar.f612a));
        editorEdit.commit();
    }
}

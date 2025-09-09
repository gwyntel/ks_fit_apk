package com.huawei.hms.utils;

import android.content.Context;
import com.huawei.hms.support.log.HMSLog;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes4.dex */
public abstract class FileUtil {
    public static final String LOCAL_REPORT_FILE = "hms/HwMobileServiceReport.txt";
    public static final String LOCAL_REPORT_FILE_CONFIG = "hms/config.txt";
    public static final long LOCAL_REPORT_FILE_MAX_SIZE = 10240;

    /* renamed from: a, reason: collision with root package name */
    private static boolean f18179a = false;

    /* renamed from: b, reason: collision with root package name */
    private static ScheduledExecutorService f18180b = Executors.newSingleThreadScheduledExecutor();

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ File f18181a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ long f18182b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ String f18183c;

        a(File file, long j2, String str) {
            this.f18181a = file;
            this.f18182b = j2;
            this.f18183c = str;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            RandomAccessFile randomAccessFile;
            Throwable th;
            File file = this.f18181a;
            if (file == null) {
                HMSLog.e("FileUtil", "In writeFile Failed to get local file.");
                return;
            }
            File parentFile = file.getParentFile();
            if (parentFile == null || !(parentFile.mkdirs() || parentFile.isDirectory())) {
                HMSLog.e("FileUtil", "In writeFile, Failed to create directory.");
                return;
            }
            RandomAccessFile randomAccessFile2 = null;
            try {
                try {
                    long length = this.f18181a.length();
                    if (length > this.f18182b) {
                        String canonicalPath = this.f18181a.getCanonicalPath();
                        if (!this.f18181a.delete()) {
                            HMSLog.e("FileUtil", "last file delete failed.");
                        }
                        randomAccessFile2 = new RandomAccessFile(new File(canonicalPath), "rw");
                    } else {
                        randomAccessFile = new RandomAccessFile(this.f18181a, "rw");
                        try {
                            randomAccessFile.seek(length);
                            randomAccessFile2 = randomAccessFile;
                        } catch (IOException e2) {
                            e = e2;
                            randomAccessFile2 = randomAccessFile;
                            HMSLog.e("FileUtil", "writeFile exception:", e);
                            IOUtils.closeQuietly(randomAccessFile2);
                        } catch (Throwable th2) {
                            th = th2;
                            IOUtils.closeQuietly(randomAccessFile);
                            throw th;
                        }
                    }
                    randomAccessFile2.writeBytes(this.f18183c + System.getProperty("line.separator"));
                } catch (IOException e3) {
                    e = e3;
                }
                IOUtils.closeQuietly(randomAccessFile2);
            } catch (Throwable th3) {
                randomAccessFile = null;
                th = th3;
            }
        }
    }

    public static boolean verifyHash(String str, File file) throws Throwable {
        byte[] bArrDigest = SHA256.digest(file);
        return bArrDigest != null && HEX.encodeHexString(bArrDigest, true).equalsIgnoreCase(str);
    }

    public static void writeFile(File file, String str, long j2) {
        f18180b.execute(new a(file, j2, str));
    }

    public static void writeFileReport(Context context, File file, File file2, String str, long j2, int i2) {
        if (file != null && file.isFile() && file.exists()) {
            if (!f18179a) {
                if (file2 != null && file2.exists() && !file2.delete()) {
                    HMSLog.e("FileUtil", "file delete failed.");
                }
                f18179a = true;
            }
            writeFile(file2, str + "|" + j2 + "|" + i2, LOCAL_REPORT_FILE_MAX_SIZE);
        }
    }
}

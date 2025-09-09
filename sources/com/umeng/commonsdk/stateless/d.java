package com.umeng.commonsdk.stateless;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.ULog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.zip.Deflater;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static int f22316a;

    /* renamed from: b, reason: collision with root package name */
    private static Object f22317b = new Object();

    public static boolean a(long j2, long j3) {
        return j2 > j3;
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x00e3 A[EXC_TOP_SPLITTER, PHI: r2 r9
      0x00e3: PHI (r2v3 java.io.FileOutputStream) = (r2v2 java.io.FileOutputStream), (r2v5 java.io.FileOutputStream) binds: [B:39:0x00e1, B:45:0x010b] A[DONT_GENERATE, DONT_INLINE]
      0x00e3: PHI (r9v4 boolean) = (r9v3 boolean), (r9v6 boolean) binds: [B:39:0x00e1, B:45:0x010b] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean b(android.content.Context r7, java.lang.String r8, java.lang.String r9, byte[] r10) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.d.b(android.content.Context, java.lang.String, java.lang.String, byte[]):boolean");
    }

    public static File[] c(Context context) {
        if (context == null) {
            return null;
        }
        try {
            synchronized (f22317b) {
                try {
                    String str = context.getApplicationContext().getFilesDir() + File.separator + a.f22295f;
                    if (TextUtils.isEmpty(str)) {
                        return null;
                    }
                    File file = new File(str);
                    synchronized (f22317b) {
                        File[] fileArrListFiles = file.listFiles();
                        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                            Arrays.sort(fileArrListFiles, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.3
                                @Override // java.util.Comparator
                                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                                public int compare(File file2, File file3) {
                                    long jLastModified = file2.lastModified() - file3.lastModified();
                                    if (jLastModified > 0) {
                                        return 1;
                                    }
                                    return jLastModified == 0 ? 0 : -1;
                                }
                            });
                            return fileArrListFiles;
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Throwable th2) {
            UMCrashManager.reportCrash(context, th2);
            return null;
        }
    }

    public static String d(String str) {
        int iLastIndexOf;
        int iLastIndexOf2;
        return (!TextUtils.isEmpty(str) && str.indexOf("envelope") < 0 && (iLastIndexOf = str.lastIndexOf(OpenAccountUIConstants.UNDER_LINE)) >= 0 && (iLastIndexOf2 = str.lastIndexOf(".")) >= 0) ? str.substring(iLastIndexOf + 1, iLastIndexOf2) : "";
    }

    public static boolean a(File file) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            for (String str : file.list()) {
                if (!a(new File(file, str))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0067 A[EXC_TOP_SPLITTER, PHI: r1
      0x0067: PHI (r1v2 java.io.FileOutputStream) = (r1v1 java.io.FileOutputStream), (r1v3 java.io.FileOutputStream) binds: [B:26:0x0065, B:32:0x0070] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(android.content.Context r6, java.lang.String r7, java.lang.String r8, byte[] r9) {
        /*
            r0 = 101(0x65, float:1.42E-43)
            if (r6 == 0) goto L79
            r1 = 0
            java.lang.Object r2 = com.umeng.commonsdk.stateless.d.f22317b     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L60
            monitor-enter(r2)     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L60
            java.io.File r3 = new java.io.File     // Catch: java.lang.Throwable -> L2f
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2f
            r4.<init>()     // Catch: java.lang.Throwable -> L2f
            java.io.File r5 = r6.getFilesDir()     // Catch: java.lang.Throwable -> L2f
            r4.append(r5)     // Catch: java.lang.Throwable -> L2f
            java.lang.String r5 = java.io.File.separator     // Catch: java.lang.Throwable -> L2f
            r4.append(r5)     // Catch: java.lang.Throwable -> L2f
            r4.append(r7)     // Catch: java.lang.Throwable -> L2f
            java.lang.String r7 = r4.toString()     // Catch: java.lang.Throwable -> L2f
            r3.<init>(r7)     // Catch: java.lang.Throwable -> L2f
            boolean r7 = r3.isDirectory()     // Catch: java.lang.Throwable -> L2f
            if (r7 != 0) goto L31
            r3.mkdir()     // Catch: java.lang.Throwable -> L2f
            goto L31
        L2f:
            r7 = move-exception
            goto L5c
        L31:
            java.io.File r7 = new java.io.File     // Catch: java.lang.Throwable -> L2f
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2f
            r4.<init>()     // Catch: java.lang.Throwable -> L2f
            java.lang.String r3 = r3.getPath()     // Catch: java.lang.Throwable -> L2f
            r4.append(r3)     // Catch: java.lang.Throwable -> L2f
            r4.append(r5)     // Catch: java.lang.Throwable -> L2f
            r4.append(r8)     // Catch: java.lang.Throwable -> L2f
            java.lang.String r8 = r4.toString()     // Catch: java.lang.Throwable -> L2f
            r7.<init>(r8)     // Catch: java.lang.Throwable -> L2f
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L2f
            r8.<init>(r7)     // Catch: java.lang.Throwable -> L2f
            r8.write(r9)     // Catch: java.lang.Throwable -> L5a
            r8.close()     // Catch: java.lang.Throwable -> L5a
            r0 = 0
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2f
            goto L79
        L5a:
            r7 = move-exception
            r1 = r8
        L5c:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L2f
            throw r7     // Catch: java.lang.Throwable -> L5e java.io.IOException -> L60
        L5e:
            r7 = move-exception
            goto L62
        L60:
            r7 = move-exception
            goto L6d
        L62:
            com.umeng.commonsdk.internal.crash.UMCrashManager.reportCrash(r6, r7)     // Catch: java.lang.Throwable -> L6b
            if (r1 == 0) goto L79
        L67:
            r1.close()     // Catch: java.lang.Throwable -> L79
            goto L79
        L6b:
            r6 = move-exception
            goto L73
        L6d:
            com.umeng.commonsdk.internal.crash.UMCrashManager.reportCrash(r6, r7)     // Catch: java.lang.Throwable -> L6b
            if (r1 == 0) goto L79
            goto L67
        L73:
            if (r1 == 0) goto L78
            r1.close()     // Catch: java.lang.Throwable -> L78
        L78:
            throw r6
        L79:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.d.a(android.content.Context, java.lang.String, java.lang.String, byte[]):int");
    }

    public static String c(String str) {
        try {
            return new String(Base64.decode(str, 0));
        } catch (Throwable unused) {
            return "";
        }
    }

    public static byte[] a(String str) throws IOException {
        byte[] bArr;
        synchronized (f22317b) {
            FileChannel channel = null;
            try {
                try {
                    channel = new RandomAccessFile(str, "r").getChannel();
                    MappedByteBuffer mappedByteBufferLoad = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size()).load();
                    bArr = new byte[(int) channel.size()];
                    if (mappedByteBufferLoad.remaining() > 0) {
                        mappedByteBufferLoad.get(bArr, 0, mappedByteBufferLoad.remaining());
                    }
                    try {
                        channel.close();
                    } catch (Throwable unused) {
                    }
                } catch (Throwable th) {
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (Throwable unused2) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                ULog.i("walle", "[stateless] write envelope, e is " + e2.getMessage());
                throw e2;
            }
        }
        return bArr;
    }

    public static File b(Context context) {
        if (context == null) {
            return null;
        }
        try {
            synchronized (f22317b) {
                try {
                    String str = context.getApplicationContext().getFilesDir() + File.separator + a.f22295f;
                    if (TextUtils.isEmpty(str)) {
                        return null;
                    }
                    File file = new File(str);
                    synchronized (f22317b) {
                        File[] fileArrListFiles = file.listFiles();
                        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                            Arrays.sort(fileArrListFiles, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.2
                                @Override // java.util.Comparator
                                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                                public int compare(File file2, File file3) {
                                    long jLastModified = file2.lastModified() - file3.lastModified();
                                    if (jLastModified > 0) {
                                        return 1;
                                    }
                                    return jLastModified == 0 ? 0 : -1;
                                }
                            });
                            return fileArrListFiles[0];
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Throwable th2) {
            UMCrashManager.reportCrash(context, th2);
            return null;
        }
    }

    public static File a(Context context) {
        File[] fileArrListFiles;
        File[] fileArrListFiles2;
        File file = null;
        try {
            synchronized (f22317b) {
                try {
                    ULog.i("walle", "get last envelope begin, thread is " + Thread.currentThread());
                    if (context != null && context.getApplicationContext() != null) {
                        String str = context.getApplicationContext().getFilesDir() + File.separator + a.f22294e;
                        if (!TextUtils.isEmpty(str)) {
                            File file2 = new File(str);
                            if (file2.isDirectory() && (fileArrListFiles = file2.listFiles()) != null && fileArrListFiles.length > 0) {
                                for (File file3 : fileArrListFiles) {
                                    if (file3 != null && file3.isDirectory() && (fileArrListFiles2 = file3.listFiles()) != null && fileArrListFiles2.length > 0) {
                                        Arrays.sort(fileArrListFiles2, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.1
                                            @Override // java.util.Comparator
                                            /* renamed from: a, reason: merged with bridge method [inline-methods] */
                                            public int compare(File file4, File file5) {
                                                long jLastModified = file4.lastModified() - file5.lastModified();
                                                if (jLastModified > 0) {
                                                    return 1;
                                                }
                                                return jLastModified == 0 ? 0 : -1;
                                            }
                                        });
                                        File file4 = fileArrListFiles2[0];
                                        if (file4 != null && (file == null || file.lastModified() > file4.lastModified())) {
                                            file = file4;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    ULog.i("walle", "get last envelope end, thread is " + Thread.currentThread());
                } finally {
                }
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
        return file;
    }

    public static String b(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            stringBuffer.append(String.format("%02X", Byte.valueOf(b2)));
        }
        return stringBuffer.toString().toLowerCase(Locale.US);
    }

    public static String b(String str) {
        try {
            return Base64.encodeToString(str.getBytes(), 0);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String a(Context context, boolean z2) {
        String str;
        String str2 = null;
        if (context == null) {
            return null;
        }
        try {
            if (z2) {
                str = context.getApplicationContext().getFilesDir() + File.separator + a.f22294e;
            } else {
                str = context.getApplicationContext().getFilesDir() + File.separator + a.f22295f;
            }
            str2 = str;
            return str2;
        } catch (Throwable unused) {
            return str2;
        }
    }

    public static void a(Context context, String str, int i2) {
        try {
            if (str == null) {
                ULog.i("AmapLBS", "[lbs-build] fileDir not exist, thread is " + Thread.currentThread());
                return;
            }
            File file = new File(str);
            if (!file.isDirectory()) {
                ULog.i("AmapLBS", "[lbs-build] fileDir not exist, thread is " + Thread.currentThread());
                return;
            }
            synchronized (f22317b) {
                try {
                    File[] fileArrListFiles = file.listFiles();
                    ULog.i("AmapLBS", "[lbs-build] delete file begin " + fileArrListFiles.length + ", thread is " + Thread.currentThread());
                    if (fileArrListFiles.length >= i2) {
                        ULog.i("AmapLBS", "[lbs-build] file size >= max");
                        ArrayList arrayList = new ArrayList();
                        for (File file2 : fileArrListFiles) {
                            if (file2 != null) {
                                arrayList.add(file2);
                            }
                        }
                        if (arrayList.size() >= i2) {
                            Collections.sort(arrayList, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.4
                                @Override // java.util.Comparator
                                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                                public int compare(File file3, File file4) {
                                    if (file3 == null || file4 == null || file3.lastModified() >= file4.lastModified()) {
                                        return (file3 == null || file4 == null || file3.lastModified() != file4.lastModified()) ? 1 : 0;
                                    }
                                    return -1;
                                }
                            });
                            if (ULog.DEBUG) {
                                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                                    ULog.i("AmapLBS", "[lbs-build] overrun native file is " + ((File) arrayList.get(i3)).getPath());
                                }
                            }
                            for (int i4 = 0; i4 <= arrayList.size() - i2; i4++) {
                                if (arrayList.get(i4) != null) {
                                    ULog.i("AmapLBS", "[lbs-build] overrun remove file is " + ((File) arrayList.get(i4)).getPath());
                                    try {
                                        ((File) arrayList.get(i4)).delete();
                                        arrayList.remove(i4);
                                    } catch (Exception unused) {
                                    }
                                }
                            }
                        }
                    } else {
                        ULog.i("AmapLBS", "[lbs-build] file size < max");
                    }
                    ULog.i("AmapLBS", "[lbs-build] delete file end " + fileArrListFiles.length + ", thread is " + Thread.currentThread());
                } finally {
                }
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
    }

    public static void a(Context context, String str, final String str2, int i2) {
        if (str == null) {
            return;
        }
        try {
            File file = new File(str);
            if (file.isDirectory()) {
                synchronized (f22317b) {
                    try {
                        File[] fileArrListFiles = file.listFiles(new FilenameFilter() { // from class: com.umeng.commonsdk.stateless.d.5
                            @Override // java.io.FilenameFilter
                            public boolean accept(File file2, String str3) {
                                return str3.startsWith(str2);
                            }
                        });
                        if (fileArrListFiles != null && fileArrListFiles.length >= i2) {
                            ULog.i("AmapLBS", "[lbs-build] file size >= max");
                            ArrayList arrayList = new ArrayList();
                            for (File file2 : fileArrListFiles) {
                                if (file2 != null) {
                                    arrayList.add(file2);
                                }
                            }
                            if (arrayList.size() >= i2) {
                                Collections.sort(arrayList, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.6
                                    @Override // java.util.Comparator
                                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                                    public int compare(File file3, File file4) {
                                        if (file3 == null || file4 == null || file3.lastModified() >= file4.lastModified()) {
                                            return (file3 == null || file4 == null || file3.lastModified() != file4.lastModified()) ? 1 : 0;
                                        }
                                        return -1;
                                    }
                                });
                                if (ULog.DEBUG) {
                                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                                        ULog.i("AmapLBS", "[lbs-build] overrun native file is " + ((File) arrayList.get(i3)).getPath());
                                    }
                                }
                                for (int i4 = 0; i4 <= arrayList.size() - i2; i4++) {
                                    if (arrayList.get(i4) != null) {
                                        ULog.i("AmapLBS", "[lbs-build] overrun remove file is " + ((File) arrayList.get(i4)).getPath());
                                        try {
                                            ((File) arrayList.get(i4)).delete();
                                            arrayList.remove(i4);
                                        } catch (Exception unused) {
                                        }
                                    }
                                }
                            }
                        } else {
                            ULog.i("AmapLBS", "[lbs-build] file size < max");
                        }
                        ULog.i("AmapLBS", "[lbs-build] delete file end " + fileArrListFiles.length + ", thread is " + Thread.currentThread());
                    } finally {
                    }
                }
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
    }

    public static byte[] a(byte[] bArr) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = null;
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        Deflater deflater = new Deflater();
        deflater.setInput(bArr);
        deflater.finish();
        byte[] bArr2 = new byte[8192];
        f22316a = 0;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            while (!deflater.finished()) {
                try {
                    int iDeflate = deflater.deflate(bArr2);
                    f22316a += iDeflate;
                    byteArrayOutputStream2.write(bArr2, 0, iDeflate);
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    throw th;
                }
            }
            deflater.end();
            byteArrayOutputStream2.close();
            return byteArrayOutputStream2.toByteArray();
        } catch (Throwable th2) {
            th = th2;
        }
    }
}

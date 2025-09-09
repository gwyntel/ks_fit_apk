package com.huawei.secure.android.common.util;

import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

/* loaded from: classes4.dex */
public class ZipUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18533a = "ZipUtil";

    /* renamed from: b, reason: collision with root package name */
    private static final int f18534b = 104857600;

    /* renamed from: c, reason: collision with root package name */
    private static final int f18535c = 100;

    /* renamed from: d, reason: collision with root package name */
    private static final int f18536d = 4096;

    /* renamed from: e, reason: collision with root package name */
    private static final String f18537e = "..";

    /* renamed from: f, reason: collision with root package name */
    private static final String[] f18538f = {"..\\", "../", "./", ".\\.\\", "%00", "..%2F", "..%5C", ".%2F"};

    /* JADX WARN: Code restructure failed: missing block: B:100:0x018b, code lost:
    
        a(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0072, code lost:
    
        android.util.Log.e(r9, "zipPath is a invalid path: " + d(r6));
        r8 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0100, code lost:
    
        r8 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0102, code lost:
    
        android.util.Log.e(r8, "unzipFileNew: over than top size");
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0105, code lost:
    
        r9 = false;
        r11 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0108, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0186, code lost:
    
        com.huawei.secure.android.common.util.IOUtil.closeSecure((java.io.Closeable) r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0189, code lost:
    
        if (r8 != false) goto L106;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01bb  */
    /* JADX WARN: Type inference failed for: r10v0, types: [java.io.Closeable, java.util.zip.ZipFile] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    @android.annotation.SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.List<java.io.File> a(java.io.File r17, java.io.File r18, long r19, boolean r21, boolean r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 448
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.secure.android.common.util.ZipUtil.a(java.io.File, java.io.File, long, boolean, boolean):java.util.List");
    }

    private static boolean b(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!a(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException unused) {
            Log.e(f18533a, "createOrExistsFile IOException ");
            return false;
        }
    }

    private static File c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return b(str);
    }

    private static String d(String str) {
        int iLastIndexOf;
        return (TextUtils.isEmpty(str) || (iLastIndexOf = str.lastIndexOf(File.separator)) == -1) ? str : str.substring(iLastIndexOf + 1);
    }

    private static void e(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            c(file);
            return;
        }
        if (file.isDirectory()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null || fileArrListFiles.length == 0) {
                c(file);
                return;
            }
            for (File file2 : fileArrListFiles) {
                e(file2);
            }
            c(file);
        }
    }

    @Deprecated
    public static boolean unZip(String str, String str2, boolean z2) throws SecurityCommonException {
        return unZip(str, str2, 104857600L, 100, z2);
    }

    public static List<File> unZipNew(String str, String str2, boolean z2) throws SecurityCommonException {
        return unZipNew(str, str2, 104857600L, 100, z2);
    }

    private static void c(File file) {
        if (file == null || file.delete()) {
            return;
        }
        LogsUtil.e(f18533a, "delete file error");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x006d, code lost:
    
        android.util.Log.e(com.huawei.secure.android.common.util.ZipUtil.f18533a, "zipPath is a invalid path: " + d(r13));
        r2 = r3;
        r10 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00d2, code lost:
    
        android.util.Log.e(com.huawei.secure.android.common.util.ZipUtil.f18533a, "unzip  over than top size");
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d7, code lost:
    
        r2 = 0;
        r10 = 0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0161  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean unZip(java.lang.String r17, java.lang.String r18, long r19, int r21, boolean r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.secure.android.common.util.ZipUtil.unZip(java.lang.String, java.lang.String, long, int, boolean):boolean");
    }

    public static List<File> unZipNew(String str, String str2, long j2, int i2, boolean z2) throws SecurityCommonException {
        if (!a(str, str2, j2, i2)) {
            return null;
        }
        String str3 = File.separator;
        if (str2.endsWith(str3) && str2.length() > str3.length()) {
            str2 = str2.substring(0, str2.length() - str3.length());
        }
        return a(c(str), c(str2), j2, z2, false);
    }

    private static void d(File file) {
        if (file == null || file.exists() || file.mkdirs()) {
            return;
        }
        LogsUtil.e(f18533a, "mkdirs error , files exists or IOException.");
    }

    private static File b(String str) {
        a(str);
        return new File(str);
    }

    private static boolean e(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.e(f18533a, "isContainInvalidStr: name is null");
            return false;
        }
        if (str.equals(f18537e)) {
            return true;
        }
        for (String str2 : f18538f) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return false;
    }

    private static void a(String str) {
        if (TextUtils.isEmpty(str) || !e(str)) {
            return;
        }
        Log.e(f18533a, "IllegalArgumentException--path is not a standard path");
        throw new IllegalArgumentException("path is not a standard path");
    }

    private static boolean a(File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0073, code lost:
    
        com.huawei.secure.android.common.util.LogsUtil.e(com.huawei.secure.android.common.util.ZipUtil.f18533a, "File name is invalid or too many files or too big");
     */
    @androidx.annotation.RequiresApi(api = 24)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(java.lang.String r18, long r19, int r21, boolean r22) throws java.io.IOException {
        /*
            r1 = r18
            r2 = r19
            r4 = r21
            java.lang.String r5 = "close zipFile IOException "
            java.lang.String r6 = "ZipUtil"
            r7 = 0
            r8 = 0
            if (r22 != 0) goto L18
            java.util.zip.ZipFile r0 = new java.util.zip.ZipFile     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
        L13:
            r7 = r0
            goto L3a
        L15:
            r0 = move-exception
            goto Lcb
        L18:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            r0.<init>()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            java.lang.String r9 = "not a utf8 zip file, use gbk open zip file : "
            r0.append(r9)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            r0.append(r1)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            com.huawei.secure.android.common.util.LogsUtil.i(r6, r0)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            com.huawei.secure.android.common.util.g.a()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            java.lang.String r0 = "GBK"
            java.nio.charset.Charset r0 = java.nio.charset.Charset.forName(r0)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            java.util.zip.ZipFile r0 = com.huawei.secure.android.common.util.h.a(r1, r0)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            goto L13
        L3a:
            java.util.Enumeration r9 = r7.entries()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            r10 = 1
            r11 = 0
            r13 = r8
            r0 = r10
        L43:
            boolean r14 = r9.hasMoreElements()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            if (r14 == 0) goto La4
            java.lang.Object r14 = r9.nextElement()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71 java.lang.IllegalArgumentException -> L79
            java.util.zip.ZipEntry r14 = (java.util.zip.ZipEntry) r14     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71 java.lang.IllegalArgumentException -> L79
            long r15 = r14.getSize()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            long r11 = r11 + r15
            int r13 = r13 + 1
            java.lang.String r15 = r14.getName()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            boolean r15 = e(r15)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            if (r15 != 0) goto L73
            if (r13 >= r4) goto L73
            int r15 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r15 > 0) goto L73
            long r14 = r14.getSize()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            r16 = -1
            int r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r14 != 0) goto L43
            goto L73
        L71:
            r0 = move-exception
            goto La9
        L73:
            java.lang.String r0 = "File name is invalid or too many files or too big"
            com.huawei.secure.android.common.util.LogsUtil.e(r6, r0)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            goto La5
        L79:
            r0 = move-exception
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            r14.<init>()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            java.lang.String r15 = "not a utf8 zip file, IllegalArgumentException : "
            r14.append(r15)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            r14.append(r0)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            java.lang.String r0 = r14.toString()     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            com.huawei.secure.android.common.util.LogsUtil.i(r6, r0)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            r14 = 24
            if (r0 < r14) goto L9d
            boolean r8 = a(r1, r2, r4, r10)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            goto La5
        L9d:
            java.lang.String r0 = "File is not a utf8 zip file and Build.VERSION_CODES < 24"
            com.huawei.secure.android.common.util.LogsUtil.e(r6, r0)     // Catch: java.lang.Throwable -> L15 java.io.IOException -> L71
            r0 = r8
            goto L43
        La4:
            r8 = r0
        La5:
            r7.close()     // Catch: java.io.IOException -> Lc7
            goto Lca
        La9:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L15
            r1.<init>()     // Catch: java.lang.Throwable -> L15
            java.lang.String r2 = "not a valid zip file, IOException : "
            r1.append(r2)     // Catch: java.lang.Throwable -> L15
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L15
            r1.append(r0)     // Catch: java.lang.Throwable -> L15
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L15
            com.huawei.secure.android.common.util.LogsUtil.e(r6, r0)     // Catch: java.lang.Throwable -> L15
            if (r7 == 0) goto Lca
            r7.close()     // Catch: java.io.IOException -> Lc7
            goto Lca
        Lc7:
            com.huawei.secure.android.common.util.LogsUtil.e(r6, r5)
        Lca:
            return r8
        Lcb:
            if (r7 == 0) goto Ld4
            r7.close()     // Catch: java.io.IOException -> Ld1
            goto Ld4
        Ld1:
            com.huawei.secure.android.common.util.LogsUtil.e(r6, r5)
        Ld4:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.secure.android.common.util.ZipUtil.a(java.lang.String, long, int, boolean):boolean");
    }

    private static boolean a(String str, String str2, long j2, int i2) throws SecurityCommonException {
        if (!TextUtils.isEmpty(str) && !e(str)) {
            if (!TextUtils.isEmpty(str2) && !e(str2)) {
                if (a(str, j2, i2, false)) {
                    return true;
                }
                LogsUtil.e(f18533a, "zip file contains valid chars or too many files");
                throw new SecurityCommonException("unsecure zipfile!");
            }
            LogsUtil.e(f18533a, "target directory is not valid");
            return false;
        }
        LogsUtil.e(f18533a, "zip file is not valid");
        return false;
    }

    private static boolean a(List<File> list) {
        try {
            Iterator<File> it = list.iterator();
            while (it.hasNext()) {
                e(it.next());
            }
            return true;
        } catch (Exception e2) {
            LogsUtil.e(f18533a, "unzip fail delete file failed" + e2.getMessage());
            return false;
        }
    }

    private static void a(FileInputStream fileInputStream, BufferedOutputStream bufferedOutputStream, ZipInputStream zipInputStream, FileOutputStream fileOutputStream) throws IOException {
        IOUtil.closeSecure((InputStream) fileInputStream);
        IOUtil.closeSecure((OutputStream) bufferedOutputStream);
        IOUtil.closeSecure((InputStream) zipInputStream);
        IOUtil.closeSecure((OutputStream) fileOutputStream);
    }
}

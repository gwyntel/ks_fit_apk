package com.huawei.hms.feature.dynamic;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.common.util.Logger;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class ModuleCopy {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16097a = "ModuleCopy";

    /* renamed from: b, reason: collision with root package name */
    public static final int f16098b = 2048;

    /* renamed from: c, reason: collision with root package name */
    public static final int f16099c = 0;

    /* renamed from: d, reason: collision with root package name */
    public static final int f16100d = 1;

    /* renamed from: e, reason: collision with root package name */
    public static final String f16101e = "module_uri_path";

    /* renamed from: f, reason: collision with root package name */
    public static final String f16102f = "loader_uri_path";

    /* renamed from: g, reason: collision with root package name */
    public static final String f16103g = "dynamic_modules";

    /* renamed from: h, reason: collision with root package name */
    public static final String f16104h = ".apk";

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String f16105a;

        public a(String str) {
            this.f16105a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                ModuleCopy.c(this.f16105a);
            } catch (SecurityException unused) {
                Logger.w(ModuleCopy.f16097a, "Delete file failed: SecurityException.");
            }
        }
    }

    public static class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ String[] f16106a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ int f16107b;

        /* renamed from: c, reason: collision with root package name */
        public final /* synthetic */ String f16108c;

        public b(String[] strArr, int i2, String str) {
            this.f16106a = strArr;
            this.f16107b = i2;
            this.f16108c = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            for (String str : this.f16106a) {
                if (Integer.parseInt(str) < this.f16107b) {
                    Logger.i(ModuleCopy.f16097a, "Delete low version:" + this.f16107b + " in modulePath.");
                    ModuleCopy.c(this.f16108c + File.separator + str);
                }
            }
        }
    }

    public static int a(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            Logger.i(f16097a, "No version dirs in module path, need mkdir.");
            return 0;
        }
        int iMax = 0;
        for (String str : strArr) {
            iMax = Math.max(Integer.parseInt(str), iMax);
        }
        return iMax;
    }

    public static void b(String str) {
        c.a(1, "DeleteFile").execute(new a(str));
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean c(java.lang.String r9) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r9)
            boolean r1 = r0.isDirectory()
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L40
            java.lang.String[] r1 = r0.list()
            if (r1 == 0) goto L40
            java.lang.String[] r1 = r0.list()
            int r4 = r1.length
            r5 = r2
            r6 = r3
        L1a:
            if (r5 >= r4) goto L41
            r7 = r1[r5]
            if (r6 == 0) goto L3c
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r9)
            java.lang.String r8 = java.io.File.separator
            r6.append(r8)
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            boolean r6 = c(r6)
            if (r6 == 0) goto L3c
            r6 = r3
            goto L3d
        L3c:
            r6 = r2
        L3d:
            int r5 = r5 + 1
            goto L1a
        L40:
            r6 = r3
        L41:
            if (r6 == 0) goto L4a
            boolean r9 = r0.delete()
            if (r9 == 0) goto L4a
            r2 = r3
        L4a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.feature.dynamic.ModuleCopy.c(java.lang.String):boolean");
    }

    public static void clearLowVersionModule(int i2, String str, String[] strArr, String str2) {
        c.a(1, str2).execute(new b(strArr, i2, str));
    }

    public static void closeQuietly(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
                Logger.e(f16097a, "An exception occurred while closing the 'Closeable' object.");
            }
        }
    }

    public static void copyModule(Context context, Bundle bundle) {
        if (context == null || bundle == null) {
            Logger.e(f16097a, "The context or module info bundle is null.");
            return;
        }
        boolean zA = a(bundle);
        a(context, bundle, 0);
        if (zA) {
            bundle.putString(com.huawei.hms.feature.dynamic.b.f16134r, bundle.getString(com.huawei.hms.feature.dynamic.b.f16130n));
        } else {
            a(context, bundle, 1);
        }
    }

    public static String getProtectedPath(Context context) throws IOException {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.createDeviceProtectedStorageContext().getDataDir().getCanonicalPath();
        }
        String canonicalPath = context.getFilesDir().getCanonicalPath();
        int iLastIndexOf = canonicalPath.lastIndexOf(File.separator);
        return iLastIndexOf <= 0 ? canonicalPath : canonicalPath.substring(0, iLastIndexOf);
    }

    public static boolean isLocalModuleFile(Context context, String str) throws IOException {
        if (context == null || TextUtils.isEmpty(str)) {
            Logger.w(f16097a, "context or filePath is null.");
            return false;
        }
        try {
            return new File(str).getCanonicalPath().startsWith(getProtectedPath(context) + File.separator + "dynamic_modules");
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean makeDirectory(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                return true;
            }
            return file.mkdirs();
        } catch (Exception e2) {
            Logger.e(f16097a, "makeDirectory Exception: " + e2.getMessage());
            return false;
        }
    }

    public static String trimLastSection(String str) {
        int iLastIndexOf = str.lastIndexOf(File.separator);
        return iLastIndexOf <= 0 ? str : str.substring(0, iLastIndexOf);
    }

    public static String a(Context context, Bundle bundle, int i2, Uri uri) {
        String str;
        String string;
        String strValueOf;
        StringBuilder sb;
        String[] list;
        int iA;
        if (i2 == 0) {
            str = com.huawei.hms.feature.dynamic.b.f16126j;
            string = bundle.getString(com.huawei.hms.feature.dynamic.b.f16126j);
            strValueOf = String.valueOf(bundle.getInt(com.huawei.hms.feature.dynamic.b.f16127k));
            sb = new StringBuilder();
        } else {
            str = com.huawei.hms.feature.dynamic.b.f16132p;
            string = bundle.getString(com.huawei.hms.feature.dynamic.b.f16132p);
            strValueOf = String.valueOf(bundle.getInt(com.huawei.hms.feature.dynamic.b.f16133q));
            sb = new StringBuilder();
        }
        sb.append(bundle.getString(str));
        sb.append(".apk");
        String string2 = sb.toString();
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getProtectedPath(context));
            String str2 = File.separator;
            sb2.append(str2);
            sb2.append("dynamic_modules");
            sb2.append(str2);
            sb2.append(string);
            String string3 = sb2.toString();
            String str3 = string3 + str2 + strValueOf;
            String str4 = str3 + str2 + string2;
            if (new File(string3).exists() && (iA = a((list = new File(string3).list()))) >= Integer.parseInt(strValueOf)) {
                clearLowVersionModule(iA, string3, list, f16097a);
                return string3 + str2 + iA + str2 + string2;
            }
            return a(context, str3, uri, str4);
        } catch (IOException e2) {
            Logger.w(f16097a, "request modulePath error: " + e2.getMessage());
            return null;
        }
    }

    public static String a(Context context, String str, Uri uri, String str2) throws Throwable {
        if (!makeDirectory(str)) {
            Logger.e(f16097a, "makeDirectory return false");
            return null;
        }
        a(context, uri, str2);
        if (com.huawei.hms.feature.dynamic.f.d.b(context, str2)) {
            return str2;
        }
        Logger.w(f16097a, "The coped module apk is invalid.");
        b(str2);
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v6 */
    public static void a(Context context, Uri uri, String str) throws Throwable {
        InputStream inputStreamOpenInputStream;
        IOException e2;
        FileNotFoundException e3;
        InputStream inputStream;
        StringBuilder sb;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e4) {
                e3 = e4;
                inputStreamOpenInputStream = null;
            } catch (IOException e5) {
                e2 = e5;
                inputStreamOpenInputStream = null;
            } catch (Throwable th) {
                th = th;
                inputStreamOpenInputStream = null;
            }
            try {
                if (inputStreamOpenInputStream == null) {
                    Logger.w(f16097a, "Get input stream failed: null.");
                    closeQuietly(inputStreamOpenInputStream);
                    closeQuietly(null);
                    return;
                }
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(str));
                try {
                    byte[] bArr = new byte[2048];
                    while (true) {
                        int i2 = inputStreamOpenInputStream.read(bArr);
                        if (i2 == -1) {
                            closeQuietly(inputStreamOpenInputStream);
                            closeQuietly(bufferedOutputStream2);
                            return;
                        }
                        bufferedOutputStream2.write(bArr, 0, i2);
                    }
                } catch (FileNotFoundException e6) {
                    bufferedOutputStream = bufferedOutputStream2;
                    e3 = e6;
                    sb = new StringBuilder();
                    sb.append("FileNotFoundException:");
                    sb.append(e3.getMessage());
                    context = inputStreamOpenInputStream;
                    Logger.e(f16097a, sb.toString());
                    closeQuietly(context);
                    closeQuietly(bufferedOutputStream);
                } catch (IOException e7) {
                    bufferedOutputStream = bufferedOutputStream2;
                    e2 = e7;
                    sb = new StringBuilder();
                    sb.append("IOException ");
                    sb.append(e2.getMessage());
                    context = inputStreamOpenInputStream;
                    Logger.e(f16097a, sb.toString());
                    closeQuietly(context);
                    closeQuietly(bufferedOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    bufferedOutputStream = bufferedOutputStream2;
                    inputStream = inputStreamOpenInputStream;
                    closeQuietly(inputStream);
                    closeQuietly(bufferedOutputStream);
                    throw th;
                }
            } catch (FileNotFoundException e8) {
                e3 = e8;
            } catch (IOException e9) {
                e2 = e9;
            } catch (Throwable th3) {
                th = th3;
                th = th;
                inputStream = inputStreamOpenInputStream;
                closeQuietly(inputStream);
                closeQuietly(bufferedOutputStream);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            inputStream = context;
        }
    }

    public static void a(Context context, Bundle bundle, int i2) {
        String string = bundle.getString(i2 == 0 ? f16101e : f16102f);
        Logger.i(f16097a, "path:" + string);
        String strA = a(context, bundle, i2, Uri.parse(string));
        if (TextUtils.isEmpty(strA)) {
            Logger.w(f16097a, "checkModulePath failed: null.");
        } else {
            bundle.putString(i2 == 0 ? com.huawei.hms.feature.dynamic.b.f16130n : com.huawei.hms.feature.dynamic.b.f16134r, strA);
        }
    }

    public static boolean a(Bundle bundle) {
        return TextUtils.equals(bundle.getString(com.huawei.hms.feature.dynamic.b.f16130n), bundle.getString(com.huawei.hms.feature.dynamic.b.f16134r));
    }
}

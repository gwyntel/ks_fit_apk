package com.huawei.secure.android.common.ssl.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public class BksUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18462a = "BksUtil";

    /* renamed from: b, reason: collision with root package name */
    private static final String f18463b = "com.huawei.hwid";

    /* renamed from: c, reason: collision with root package name */
    private static final String f18464c = "com.huawei.hwid";

    /* renamed from: d, reason: collision with root package name */
    private static final String f18465d = "com.huawei.hms";

    /* renamed from: e, reason: collision with root package name */
    private static final String f18466e = "com.huawei.hwid.tv";

    /* renamed from: g, reason: collision with root package name */
    private static final String f18468g = "files/hmsrootcas.bks";

    /* renamed from: h, reason: collision with root package name */
    private static final String f18469h = "4.0.2.300";

    /* renamed from: i, reason: collision with root package name */
    private static final String f18470i = "aegis";

    /* renamed from: j, reason: collision with root package name */
    private static final String f18471j = "hmsrootcas.bks";

    /* renamed from: k, reason: collision with root package name */
    private static final long f18472k = 604800000;

    /* renamed from: l, reason: collision with root package name */
    private static final String f18473l = "last_update_time";

    /* renamed from: m, reason: collision with root package name */
    private static final String f18474m = "B92825C2BD5D6D6D1E7F39EECD17843B7D9016F611136B75441BC6F4D3F00F05";

    /* renamed from: n, reason: collision with root package name */
    private static final String f18475n = "3517262215D8D3008CBF888750B6418EDC4D562AC33ED6874E0D73ABA667BC3C";

    /* renamed from: q, reason: collision with root package name */
    private static final String f18478q = "";

    /* renamed from: r, reason: collision with root package name */
    private static final String f18479r = "bks_hash";

    /* renamed from: f, reason: collision with root package name */
    private static final Uri f18467f = Uri.parse("content://com.huawei.hwid");

    /* renamed from: o, reason: collision with root package name */
    private static final String f18476o = "E49D5C2C0E11B3B1B96CA56C6DE2A14EC7DAB5CCC3B5F300D03E5B4DBA44F539";

    /* renamed from: p, reason: collision with root package name */
    private static final String[] f18477p = {"B92825C2BD5D6D6D1E7F39EECD17843B7D9016F611136B75441BC6F4D3F00F05", f18476o};

    private BksUtil() {
    }

    private static boolean a(int i2) {
        return i2 >= 40002300;
    }

    private static String b(Context context) {
        return a(context) + File.separator + "hmsrootcas.bks";
    }

    private static boolean c(Context context) {
        return new File(a(context) + File.separator + "hmsrootcas.bks").exists();
    }

    public static synchronized InputStream getBksFromTss(Context context) {
        InputStream inputStream;
        InputStream inputStreamOpenInputStream;
        InputStream byteArrayInputStream;
        try {
            e.c(f18462a, "get bks from tss begin");
            if (context != null) {
                ContextUtil.setContext(context);
            }
            Context contextUtil = ContextUtil.getInstance();
            InputStream inputStream2 = null;
            if (contextUtil == null) {
                e.b(f18462a, "context is null");
                return null;
            }
            if (!b(f.a("com.huawei.hwid")) && !b(f.a("com.huawei.hms"))) {
                e.b(f18462a, "hms version code is too low : " + f.a("com.huawei.hwid"));
                return null;
            }
            if (!c(contextUtil, "com.huawei.hwid") && !b(contextUtil, "com.huawei.hms")) {
                e.b(f18462a, "hms sign error");
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                inputStreamOpenInputStream = contextUtil.getContentResolver().openInputStream(Uri.withAppendedPath(f18467f, f18468g));
                try {
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i2 = inputStreamOpenInputStream.read(bArr);
                            if (i2 <= -1) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, i2);
                        }
                        byteArrayOutputStream.flush();
                        byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                        try {
                            String strA = g.a(f18479r, "", contextUtil);
                            String strB = b(byteArrayOutputStream.toByteArray());
                            if (c(contextUtil) && strA.equals(strB)) {
                                e.c(f18462a, "bks not update");
                            } else {
                                e.c(f18462a, "update bks and sp");
                                a(byteArrayInputStream, contextUtil);
                                g.b(f18479r, strB, contextUtil);
                            }
                            d.a(inputStreamOpenInputStream);
                            d.a((OutputStream) byteArrayOutputStream);
                            d.a(byteArrayInputStream);
                        } catch (Exception e2) {
                            e = e2;
                            inputStream2 = byteArrayInputStream;
                            e.b(f18462a, "Get bks from HMS_VERSION_CODE exception : No content provider" + e.getMessage());
                            d.a(inputStreamOpenInputStream);
                            d.a((OutputStream) byteArrayOutputStream);
                            d.a(inputStream2);
                            return getFilesBksIS(contextUtil);
                        } catch (Throwable th) {
                            th = th;
                            d.a(inputStreamOpenInputStream);
                            d.a((OutputStream) byteArrayOutputStream);
                            d.a(byteArrayInputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        InputStream inputStream3 = inputStreamOpenInputStream;
                        inputStream = inputStream2;
                        inputStream2 = inputStream3;
                        byteArrayInputStream = inputStream;
                        inputStreamOpenInputStream = inputStream2;
                        d.a(inputStreamOpenInputStream);
                        d.a((OutputStream) byteArrayOutputStream);
                        d.a(byteArrayInputStream);
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
                inputStreamOpenInputStream = null;
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
                byteArrayInputStream = inputStream;
                inputStreamOpenInputStream = inputStream2;
                d.a(inputStreamOpenInputStream);
                d.a((OutputStream) byteArrayOutputStream);
                d.a(byteArrayInputStream);
                throw th;
            }
            return getFilesBksIS(contextUtil);
        } finally {
        }
    }

    public static InputStream getFilesBksIS(Context context) {
        if (!c(context)) {
            return null;
        }
        e.c(f18462a, "getFilesBksIS ");
        try {
            return new FileInputStream(b(context));
        } catch (FileNotFoundException unused) {
            e.b(f18462a, "FileNotFoundExceptio: ");
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    private static void a(InputStream inputStream, Context context) throws Throwable {
        if (inputStream == null || context == null) {
            return;
        }
        String strA = a(context);
        if (!new File(strA).exists()) {
            a(strA);
        }
        File file = new File(strA, "hmsrootcas.bks");
        if (file.exists()) {
            file.delete();
        }
        ?? r7 = 0;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                e.c(f18462a, "write output stream ");
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                r7 = 2048;
                try {
                    byte[] bArr = new byte[2048];
                    while (true) {
                        int i2 = inputStream.read(bArr, 0, 2048);
                        if (i2 == -1) {
                            break;
                        } else {
                            fileOutputStream2.write(bArr, 0, i2);
                        }
                    }
                    d.a((OutputStream) fileOutputStream2);
                } catch (IOException unused) {
                    fileOutputStream = fileOutputStream2;
                    e.b(f18462a, " IOException");
                    d.a((OutputStream) fileOutputStream);
                    r7 = fileOutputStream;
                } catch (Throwable th) {
                    th = th;
                    r7 = fileOutputStream2;
                    d.a((OutputStream) r7);
                    throw th;
                }
            } catch (IOException unused2) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static boolean b(String str) throws NumberFormatException {
        int i2;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        e.c(f18462a, "hms version code is : " + str);
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = f18469h.split("\\.");
        int length = strArrSplit.length;
        int length2 = strArrSplit2.length;
        int iMax = Math.max(length, length2);
        int i3 = 0;
        while (i3 < iMax) {
            if (i3 < length) {
                try {
                    i2 = Integer.parseInt(strArrSplit[i3]);
                } catch (Exception e2) {
                    e.b(f18462a, " exception : " + e2.getMessage());
                    return i3 >= length2;
                }
            } else {
                i2 = 0;
            }
            int i4 = i3 < length2 ? Integer.parseInt(strArrSplit2[i3]) : 0;
            if (i2 < i4) {
                return false;
            }
            if (i2 > i4) {
                return true;
            }
            i3++;
        }
        return true;
    }

    private static boolean c(Context context, String str) {
        byte[] bArrA = a(context, str);
        for (String str2 : f18477p) {
            if (str2.equalsIgnoreCase(c(bArrA))) {
                return true;
            }
        }
        return false;
    }

    private static String c(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        try {
            return a(MessageDigest.getInstance("SHA-256").digest(bArr));
        } catch (NoSuchAlgorithmException e2) {
            Log.e(f18462a, "NoSuchAlgorithmException" + e2.getMessage());
            return "";
        }
    }

    private static boolean b(Context context, String str) {
        return f18476o.equalsIgnoreCase(c(a(context, str)));
    }

    private static String b(byte[] bArr) throws NoSuchAlgorithmException {
        if (bArr == null) {
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr);
            return a(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            e.b(f18462a, "inputstraem exception");
            return "";
        }
    }

    private static int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        File file = new File(str);
        if (file.exists()) {
            e.e(f18462a, "The directory  has already exists");
            return 1;
        }
        if (file.mkdirs()) {
            e.a(f18462a, "create directory  success");
            return 0;
        }
        e.b(f18462a, "create directory  failed");
        return -1;
    }

    private static String a(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.createDeviceProtectedStorageContext().getFilesDir() + File.separator + f18470i;
        }
        return context.getApplicationContext().getFilesDir() + File.separator + f18470i;
    }

    private static byte[] a(Context context, String str) {
        PackageInfo packageInfo;
        if (context != null && !TextUtils.isEmpty(str)) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null && (packageInfo = packageManager.getPackageInfo(str, 64)) != null) {
                    return packageInfo.signatures[0].toByteArray();
                }
            } catch (PackageManager.NameNotFoundException e2) {
                Log.e(f18462a, "PackageManager.NameNotFoundException : " + e2.getMessage());
            } catch (Exception e3) {
                Log.e(f18462a, "get pm exception : " + e3.getMessage());
            }
            return new byte[0];
        }
        Log.e(f18462a, "packageName is null or context is null");
        return new byte[0];
    }

    private static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
        }
        return sb.toString();
    }
}

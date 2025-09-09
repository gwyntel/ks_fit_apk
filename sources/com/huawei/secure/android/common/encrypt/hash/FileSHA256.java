package com.huawei.secure.android.common.encrypt.hash;

import android.app.backup.BackupDataInputStream;
import android.text.TextUtils;
import com.huawei.secure.android.common.encrypt.utils.HexUtil;
import com.huawei.secure.android.common.encrypt.utils.a;
import com.huawei.secure.android.common.encrypt.utils.b;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public abstract class FileSHA256 {

    /* renamed from: a, reason: collision with root package name */
    private static final int f18280a = 8192;

    /* renamed from: c, reason: collision with root package name */
    private static final String f18282c = "FileSHA256";

    /* renamed from: d, reason: collision with root package name */
    private static final String f18283d = "";

    /* renamed from: b, reason: collision with root package name */
    private static final String f18281b = "SHA-256";

    /* renamed from: e, reason: collision with root package name */
    private static final String[] f18284e = {f18281b, "SHA-384", "SHA-512"};

    private static boolean a(File file) {
        return file != null && file.exists() && file.length() > 0;
    }

    public static String fileSHA256Encrypt(File file) {
        return fileSHAEncrypt(file, f18281b);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String fileSHAEncrypt(File file, String str) throws Throwable {
        FileInputStream fileInputStream;
        if (TextUtils.isEmpty(str) || !a(str)) {
            b.b(f18282c, "algorithm is empty or not safe");
            return "";
        }
        if (!a(file)) {
            b.b(f18282c, "file is not valid");
            return "";
        }
        BackupDataInputStream backupDataInputStream = 0;
        String strByteArray2HexStr = null;
        try {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(str);
                fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[8192];
                    boolean z2 = false;
                    while (true) {
                        int i2 = fileInputStream.read(bArr);
                        if (i2 <= 0) {
                            break;
                        }
                        messageDigest.update(bArr, 0, i2);
                        z2 = true;
                    }
                    strByteArray2HexStr = z2 ? HexUtil.byteArray2HexStr(messageDigest.digest()) : null;
                    a.a((InputStream) fileInputStream);
                } catch (IOException e2) {
                    e = e2;
                    b.b(f18282c, "IOException" + e.getMessage());
                    a.a((InputStream) fileInputStream);
                    return strByteArray2HexStr;
                } catch (NoSuchAlgorithmException e3) {
                    e = e3;
                    b.b(f18282c, "NoSuchAlgorithmException" + e.getMessage());
                    a.a((InputStream) fileInputStream);
                    return strByteArray2HexStr;
                }
            } catch (IOException e4) {
                e = e4;
                fileInputStream = null;
            } catch (NoSuchAlgorithmException e5) {
                e = e5;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                a.a((InputStream) backupDataInputStream);
                throw th;
            }
            return strByteArray2HexStr;
        } catch (Throwable th2) {
            th = th2;
            backupDataInputStream = "";
            a.a((InputStream) backupDataInputStream);
            throw th;
        }
    }

    public static String inputStreamSHA256Encrypt(InputStream inputStream) {
        return inputStream == null ? "" : inputStreamSHAEncrypt(inputStream, f18281b);
    }

    public static String inputStreamSHAEncrypt(InputStream inputStream, String str) throws IOException {
        if (inputStream == null) {
            return "";
        }
        byte[] bArr = new byte[8192];
        try {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(str);
                while (true) {
                    int i2 = inputStream.read(bArr);
                    if (i2 < 0) {
                        String strByteArray2HexStr = HexUtil.byteArray2HexStr(messageDigest.digest());
                        a.a(inputStream);
                        return strByteArray2HexStr;
                    }
                    if (i2 > 0) {
                        messageDigest.update(bArr, 0, i2);
                    }
                }
            } catch (IOException | NoSuchAlgorithmException unused) {
                b.b(f18282c, "inputstraem exception");
                a.a(inputStream);
                return "";
            }
        } catch (Throwable th) {
            a.a(inputStream);
            throw th;
        }
    }

    public static boolean validateFileSHA(File file, String str, String str2) {
        if (!TextUtils.isEmpty(str) && a(str2)) {
            return str.equals(fileSHAEncrypt(file, str2));
        }
        b.b(f18282c, "hash value is null || algorithm is illegal");
        return false;
    }

    public static boolean validateFileSHA256(File file, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.equalsIgnoreCase(fileSHA256Encrypt(file));
    }

    public static boolean validateInputStreamSHA(InputStream inputStream, String str, String str2) {
        if (!TextUtils.isEmpty(str) && a(str2)) {
            return str.equals(inputStreamSHAEncrypt(inputStream, str2));
        }
        b.b(f18282c, "hash value is null || algorithm is illegal");
        return false;
    }

    public static boolean validateInputStreamSHA256(InputStream inputStream, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.equals(inputStreamSHA256Encrypt(inputStream));
    }

    private static boolean a(String str) {
        for (String str2 : f18284e) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }
}

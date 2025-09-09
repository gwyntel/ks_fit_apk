package com.huawei.hms.opendevice;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.huawei.android.hms.openid.R;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.IOUtils;
import com.huawei.secure.android.common.encrypt.utils.BaseKeyUtil;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;
import com.huawei.secure.android.common.encrypt.utils.RootKeyUtil;
import com.huawei.secure.android.common.encrypt.utils.WorkKeyCryptUtil;
import com.huawei.secure.android.common.util.IOUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class o {

    /* renamed from: a, reason: collision with root package name */
    private static final String f16623a = "o";

    /* renamed from: b, reason: collision with root package name */
    private static Map<String, String> f16624b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    private static final Object f16625c = new Object();

    private static byte[] a(String str, String str2, String str3, String str4) {
        return Build.VERSION.SDK_INT >= 26 ? BaseKeyUtil.exportRootKey(str, str2, str3, str4, 32, true) : BaseKeyUtil.exportRootKey(str, str2, str3, str4, 32, false);
    }

    private static byte[] b() {
        return a(d(), e(), c(), g());
    }

    public static void c(Context context) {
        synchronized (f16625c) {
            try {
                d(context.getApplicationContext());
                if (i()) {
                    HMSLog.i(f16623a, "The local secret is already in separate file mode.");
                    return;
                }
                File file = new File(d.c(context.getApplicationContext()) + "/shared_prefs/LocalAvengers.xml");
                if (file.exists()) {
                    IOUtil.deleteSecure(file);
                    HMSLog.i(f16623a, "destroy C, delete file LocalAvengers.xml.");
                }
                byte[] bArrGenerateSecureRandom = EncryptUtil.generateSecureRandom(32);
                byte[] bArrGenerateSecureRandom2 = EncryptUtil.generateSecureRandom(32);
                byte[] bArrGenerateSecureRandom3 = EncryptUtil.generateSecureRandom(32);
                byte[] bArrGenerateSecureRandom4 = EncryptUtil.generateSecureRandom(32);
                String strA = c.a(bArrGenerateSecureRandom);
                String strA2 = c.a(bArrGenerateSecureRandom2);
                String strA3 = c.a(bArrGenerateSecureRandom3);
                String strA4 = c.a(bArrGenerateSecureRandom4);
                a(strA, strA2, strA3, strA4, WorkKeyCryptUtil.encryptWorkKey(c.a(EncryptUtil.generateSecureRandom(32)), a(strA, strA2, strA3, strA4)), context);
                HMSLog.i(f16623a, "generate D.");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static void d(Context context) throws Throwable {
        if (i()) {
            HMSLog.i(f16623a, "secretKeyCache not empty.");
            return;
        }
        f16624b.clear();
        String strC = d.c(context);
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        String strA = p.a(strC + "/files/math/m");
        String strA2 = p.a(strC + "/files/panda/p");
        String strA3 = p.a(strC + "/files/panda/d");
        String strA4 = p.a(strC + "/files/math/t");
        String strA5 = p.a(strC + "/files/s");
        if (q.a(strA, strA2, strA3, strA4, strA5)) {
            f16624b.put("m", strA);
            f16624b.put("p", strA2);
            f16624b.put("d", strA3);
            f16624b.put("t", strA4);
            f16624b.put("s", strA5);
        }
    }

    private static synchronized String e(Context context) {
        String strDecryptWorkKey = WorkKeyCryptUtil.decryptWorkKey(f(), b());
        if (q.a(strDecryptWorkKey)) {
            HMSLog.i(f16623a, "keyS has been upgraded, no require operate again.");
            return strDecryptWorkKey;
        }
        String strDecryptWorkKey2 = WorkKeyCryptUtil.decryptWorkKey(f(), h());
        if (q.a(strDecryptWorkKey2)) {
            HMSLog.i(f16623a, "keyS is encrypt by RootKeyUtil, upgrade encrypt mode.");
            a(WorkKeyCryptUtil.encryptWorkKey(strDecryptWorkKey2, b()), context);
            return strDecryptWorkKey2;
        }
        String strDecryptWorkKey3 = WorkKeyCryptUtil.decryptWorkKey(f(), BaseKeyUtil.exportRootKey(d(), e(), c(), g(), 32, false));
        if (!q.a(strDecryptWorkKey3)) {
            HMSLog.e(f16623a, "all mode unable to decrypt root key.");
            return "";
        }
        HMSLog.i(f16623a, "keyS is encrypt by ExportRootKey with sha1, upgrade encrypt mode to sha256.");
        a(WorkKeyCryptUtil.encryptWorkKey(strDecryptWorkKey3, b()), context);
        return strDecryptWorkKey3;
    }

    private static String f() {
        return a("s");
    }

    private static String g() {
        return a("t");
    }

    private static RootKeyUtil h() {
        return RootKeyUtil.newInstance(d(), e(), c(), g());
    }

    private static boolean i() {
        return !TextUtils.isEmpty(f());
    }

    public static String b(Context context) {
        if (!i()) {
            HMSLog.i(f16623a, "work key is empty, execute init.");
            c(context);
        }
        String strDecryptWorkKey = WorkKeyCryptUtil.decryptWorkKey(f(), b());
        return q.a(strDecryptWorkKey) ? strDecryptWorkKey : e(context);
    }

    public static byte[] a(Context context) {
        byte[] bArrA = c.a(context.getString(R.string.push_cat_head));
        byte[] bArrA2 = c.a(context.getString(R.string.push_cat_body));
        return a(a(a(bArrA, bArrA2), c.a(a())));
    }

    private static String a() {
        return "2A57086C86EF54970C1E6EB37BFC72B1";
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        if (bArr != null && bArr2 != null && bArr.length != 0 && bArr2.length != 0) {
            int length = bArr.length;
            if (length != bArr2.length) {
                return new byte[0];
            }
            byte[] bArr3 = new byte[length];
            for (int i2 = 0; i2 < length; i2++) {
                bArr3[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
            }
            return bArr3;
        }
        return new byte[0];
    }

    private static byte[] a(byte[] bArr) {
        if (bArr != null && bArr.length != 0) {
            for (int i2 = 0; i2 < bArr.length; i2++) {
                bArr[i2] = (byte) (bArr[i2] >> 2);
            }
            return bArr;
        }
        return new byte[0];
    }

    private static String d() {
        return a("m");
    }

    private static String e() {
        return a("p");
    }

    private static void a(String str, String str2, String str3, String str4, String str5, Context context) throws Throwable {
        String strC = d.c(context.getApplicationContext());
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        try {
            a("m", str, strC + "/files/math/m");
            a("p", str2, strC + "/files/panda/p");
            a("d", str3, strC + "/files/panda/d");
            a("t", str4, strC + "/files/math/t");
            a("s", str5, strC + "/files/s");
        } catch (IOException unused) {
            HMSLog.e(f16623a, "save key IOException.");
        }
    }

    private static String c() {
        return a("d");
    }

    private static void a(String str, Context context) throws Throwable {
        String strC = d.c(context.getApplicationContext());
        if (TextUtils.isEmpty(strC)) {
            return;
        }
        try {
            a("s", str, strC + "/files/s");
        } catch (IOException unused) {
            HMSLog.e(f16623a, "save keyS IOException.");
        }
    }

    private static void a(String str, String str2, String str3) throws Throwable {
        OutputStreamWriter outputStreamWriter;
        BufferedWriter bufferedWriter;
        HMSLog.i(f16623a, "save local secret key.");
        BufferedWriter bufferedWriter2 = null;
        try {
            File file = new File(str3);
            p.a(file);
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            try {
                bufferedWriter = new BufferedWriter(outputStreamWriter);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            outputStreamWriter = null;
        }
        try {
            bufferedWriter.write(str2);
            bufferedWriter.flush();
            f16624b.put(str, str2);
            IOUtils.closeQuietly((Writer) outputStreamWriter);
            IOUtils.closeQuietly((Writer) bufferedWriter);
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2 = bufferedWriter;
            IOUtils.closeQuietly((Writer) outputStreamWriter);
            IOUtils.closeQuietly((Writer) bufferedWriter2);
            throw th;
        }
    }

    private static String a(String str) {
        String str2 = f16624b.get(str);
        return TextUtils.isEmpty(str2) ? "" : str2;
    }
}

package com.huawei.secure.android.common.ssl;

import android.annotation.SuppressLint;
import android.content.Context;
import com.huawei.secure.android.common.ssl.util.BksUtil;
import com.huawei.secure.android.common.ssl.util.ContextUtil;
import com.huawei.secure.android.common.ssl.util.e;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

/* loaded from: classes4.dex */
public class SSFSecureX509SingleInstance {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18378a = "SSFSecureX509SingleInstance";

    /* renamed from: b, reason: collision with root package name */
    private static volatile SecureX509TrustManager f18379b;

    private SSFSecureX509SingleInstance() {
    }

    @SuppressLint({"NewApi"})
    public static SecureX509TrustManager getInstance(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException {
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        ContextUtil.setContext(context);
        if (f18379b == null) {
            synchronized (SSFSecureX509SingleInstance.class) {
                try {
                    if (f18379b == null) {
                        InputStream filesBksIS = BksUtil.getFilesBksIS(context);
                        if (filesBksIS == null) {
                            e.c(f18378a, "get assets bks");
                            filesBksIS = context.getAssets().open("hmsrootcas.bks");
                        } else {
                            e.c(f18378a, "get files bks");
                        }
                        f18379b = new SecureX509TrustManager(filesBksIS, "", true);
                    }
                } finally {
                }
            }
        }
        return f18379b;
    }

    @Deprecated
    public static void updateBks(InputStream inputStream) {
        String str = f18378a;
        e.c(str, "update bks");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (inputStream != null && f18379b != null) {
            f18379b = new SecureX509TrustManager(inputStream, "", true);
            e.a(str, "updateBks: new SecureX509TrustManager cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
            SSFCompatiableSystemCA.a(f18379b);
            SASFCompatiableSystemCA.a(f18379b);
        }
        e.a(str, "update bks cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    public static void updateBks(InputStream inputStream, SecureRandom secureRandom) {
        String str = f18378a;
        e.c(str, "update bks");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (inputStream != null && f18379b != null) {
            f18379b = new SecureX509TrustManager(inputStream, "", true);
            e.a(str, "updateBks: new SecureX509TrustManager cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
            SSFCompatiableSystemCA.a(f18379b, secureRandom);
            SASFCompatiableSystemCA.a(f18379b, secureRandom);
        }
        e.a(str, "update bks cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }
}

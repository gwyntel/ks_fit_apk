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
public class SecureX509SingleInstance {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18418a = "SecureX509SingleInstance";

    /* renamed from: b, reason: collision with root package name */
    private static volatile SecureX509TrustManager f18419b;

    private SecureX509SingleInstance() {
    }

    @SuppressLint({"NewApi"})
    public static SecureX509TrustManager getInstance(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException {
        InputStream inputStreamOpen;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (context == null) {
            throw new NullPointerException("context is null");
        }
        ContextUtil.setContext(context);
        if (f18419b == null) {
            synchronized (SecureX509SingleInstance.class) {
                if (f18419b == null) {
                    try {
                        inputStreamOpen = BksUtil.getFilesBksIS(context);
                    } catch (RuntimeException unused) {
                        e.b(f18418a, "get files bks error");
                        inputStreamOpen = null;
                    }
                    if (inputStreamOpen == null) {
                        e.c(f18418a, "get assets bks");
                        inputStreamOpen = context.getAssets().open("hmsrootcas.bks");
                    } else {
                        e.c(f18418a, "get files bks");
                    }
                    f18419b = new SecureX509TrustManager(inputStreamOpen, "");
                }
            }
        }
        e.a(f18418a, "SecureX509TrustManager getInstance: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        return f18419b;
    }

    @Deprecated
    public static void updateBks(InputStream inputStream) {
        String str = f18418a;
        e.c(str, "update bks");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (inputStream != null && f18419b != null) {
            f18419b = new SecureX509TrustManager(inputStream, "");
            SecureSSLSocketFactory.a(f18419b);
            SecureApacheSSLSocketFactory.a(f18419b);
        }
        e.c(str, "SecureX509TrustManager update bks cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    public static void updateBks(InputStream inputStream, SecureRandom secureRandom) {
        String str = f18418a;
        e.c(str, "update bks");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (inputStream != null && f18419b != null) {
            f18419b = new SecureX509TrustManager(inputStream, "");
            SecureSSLSocketFactory.a(f18419b, secureRandom);
            SecureApacheSSLSocketFactory.a(f18419b, secureRandom);
        }
        e.c(str, "SecureX509TrustManager update bks cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }
}

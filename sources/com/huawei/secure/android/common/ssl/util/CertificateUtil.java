package com.huawei.secure.android.common.ssl.util;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/* loaded from: classes4.dex */
public final class CertificateUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18480a = "CertificateUtil";

    private CertificateUtil() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.io.InputStream] */
    public static X509Certificate getHwCbgRootCA(Context context) throws Throwable {
        InputStream inputStreamOpen;
        InputStream inputStream = null;
        X509Certificate x509Certificate = null;
        try {
            try {
                KeyStore keyStore = KeyStore.getInstance(h.f18497e);
                inputStreamOpen = context.getAssets().open("hmsrootcas.bks");
                try {
                    inputStreamOpen.reset();
                    keyStore.load(inputStreamOpen, "".toCharArray());
                    x509Certificate = (X509Certificate) keyStore.getCertificate(h.f18498f);
                    context = inputStreamOpen;
                } catch (IOException e2) {
                    e = e2;
                    e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                    context = inputStreamOpen;
                    d.a((InputStream) context);
                    return x509Certificate;
                } catch (RuntimeException e3) {
                    e = e3;
                    e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                    context = inputStreamOpen;
                    d.a((InputStream) context);
                    return x509Certificate;
                } catch (KeyStoreException e4) {
                    e = e4;
                    e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                    context = inputStreamOpen;
                    d.a((InputStream) context);
                    return x509Certificate;
                } catch (NoSuchAlgorithmException e5) {
                    e = e5;
                    e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                    context = inputStreamOpen;
                    d.a((InputStream) context);
                    return x509Certificate;
                } catch (CertificateException e6) {
                    e = e6;
                    e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                    context = inputStreamOpen;
                    d.a((InputStream) context);
                    return x509Certificate;
                }
            } catch (IOException e7) {
                e = e7;
                inputStreamOpen = null;
                e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                d.a((InputStream) context);
                return x509Certificate;
            } catch (RuntimeException e8) {
                e = e8;
                inputStreamOpen = null;
                e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                d.a((InputStream) context);
                return x509Certificate;
            } catch (KeyStoreException e9) {
                e = e9;
                inputStreamOpen = null;
                e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                d.a((InputStream) context);
                return x509Certificate;
            } catch (NoSuchAlgorithmException e10) {
                e = e10;
                inputStreamOpen = null;
                e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                d.a((InputStream) context);
                return x509Certificate;
            } catch (CertificateException e11) {
                e = e11;
                inputStreamOpen = null;
                e.b(f18480a, "loadBksCA: exception : " + e.getMessage());
                context = inputStreamOpen;
                d.a((InputStream) context);
                return x509Certificate;
            } catch (Throwable th) {
                th = th;
                d.a(inputStream);
                throw th;
            }
            d.a((InputStream) context);
            return x509Certificate;
        } catch (Throwable th2) {
            inputStream = context;
            th = th2;
            d.a(inputStream);
            throw th;
        }
    }
}

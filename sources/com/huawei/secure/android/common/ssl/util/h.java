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
public class h {

    /* renamed from: b, reason: collision with root package name */
    private static final String f18494b = "X509CertificateUtil";

    /* renamed from: c, reason: collision with root package name */
    public static final String f18495c = "hmsrootcas.bks";

    /* renamed from: d, reason: collision with root package name */
    public static final String f18496d = "";

    /* renamed from: e, reason: collision with root package name */
    public static final String f18497e = "bks";

    /* renamed from: f, reason: collision with root package name */
    public static final String f18498f = "052root";

    /* renamed from: g, reason: collision with root package name */
    private static final String f18499g = "hmsincas.bks";

    /* renamed from: h, reason: collision with root package name */
    private static final String f18500h = "huawei cbg application integration ca";

    /* renamed from: a, reason: collision with root package name */
    private Context f18501a;

    public h(Context context) {
        this.f18501a = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    public X509Certificate a(String str, String str2) throws Throwable {
        InputStream inputStreamOpen;
        KeyStore keyStore;
        InputStream inputStream = null;
        X509Certificate x509Certificate = null;
        try {
            try {
                keyStore = KeyStore.getInstance(f18497e);
                inputStreamOpen = this.f18501a.getAssets().open(str);
            } catch (IOException e2) {
                e = e2;
                inputStreamOpen = null;
                e.b(f18494b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                d.a((InputStream) str);
                return x509Certificate;
            } catch (KeyStoreException e3) {
                e = e3;
                inputStreamOpen = null;
                e.b(f18494b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                d.a((InputStream) str);
                return x509Certificate;
            } catch (NoSuchAlgorithmException e4) {
                e = e4;
                inputStreamOpen = null;
                e.b(f18494b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                d.a((InputStream) str);
                return x509Certificate;
            } catch (CertificateException e5) {
                e = e5;
                inputStreamOpen = null;
                e.b(f18494b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                d.a((InputStream) str);
                return x509Certificate;
            } catch (Throwable th) {
                th = th;
                d.a(inputStream);
                throw th;
            }
            try {
                inputStreamOpen.reset();
                keyStore.load(inputStreamOpen, "".toCharArray());
                x509Certificate = (X509Certificate) keyStore.getCertificate(str2);
                str = inputStreamOpen;
            } catch (IOException e6) {
                e = e6;
                e.b(f18494b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                d.a((InputStream) str);
                return x509Certificate;
            } catch (KeyStoreException e7) {
                e = e7;
                e.b(f18494b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                d.a((InputStream) str);
                return x509Certificate;
            } catch (NoSuchAlgorithmException e8) {
                e = e8;
                e.b(f18494b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                d.a((InputStream) str);
                return x509Certificate;
            } catch (CertificateException e9) {
                e = e9;
                e.b(f18494b, "loadBksCA: exception : " + e.getMessage());
                str = inputStreamOpen;
                d.a((InputStream) str);
                return x509Certificate;
            }
            d.a((InputStream) str);
            return x509Certificate;
        } catch (Throwable th2) {
            th = th2;
            inputStream = str;
            d.a(inputStream);
            throw th;
        }
    }

    public X509Certificate b() {
        return a("hmsrootcas.bks", f18498f);
    }

    public X509Certificate a() {
        return a(f18499g, f18500h);
    }
}

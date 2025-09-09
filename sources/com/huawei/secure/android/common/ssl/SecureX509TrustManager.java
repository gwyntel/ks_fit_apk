package com.huawei.secure.android.common.ssl;

import android.content.Context;
import com.huawei.secure.android.common.ssl.util.ContextUtil;
import com.huawei.secure.android.common.ssl.util.d;
import com.huawei.secure.android.common.ssl.util.e;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes4.dex */
public class SecureX509TrustManager implements X509TrustManager {

    /* renamed from: c, reason: collision with root package name */
    private static final String f18420c = "SX509TM";

    /* renamed from: d, reason: collision with root package name */
    public static final String f18421d = "hmsrootcas.bks";

    /* renamed from: e, reason: collision with root package name */
    private static final String f18422e = "";

    /* renamed from: f, reason: collision with root package name */
    private static final String f18423f = "X509";

    /* renamed from: g, reason: collision with root package name */
    private static final String f18424g = "bks";

    /* renamed from: h, reason: collision with root package name */
    private static final String f18425h = "AndroidCAStore";

    /* renamed from: a, reason: collision with root package name */
    protected List<X509TrustManager> f18426a;

    /* renamed from: b, reason: collision with root package name */
    private X509Certificate[] f18427b;

    public SecureX509TrustManager(Context context) throws NoSuchAlgorithmException, IOException, CertificateException, KeyStoreException, IllegalArgumentException {
        this(context, false);
    }

    private void a() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        e.c(f18420c, "loadSystemCA");
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            KeyStore keyStore = KeyStore.getInstance(f18425h);
            keyStore.load(null, null);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(f18423f);
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            for (TrustManager trustManager : trustManagers) {
                if (trustManager instanceof X509TrustManager) {
                    this.f18426a.add((X509TrustManager) trustManager);
                }
            }
        } catch (IOException | NegativeArraySizeException | OutOfMemoryError | KeyStoreException | NoSuchAlgorithmException | CertificateException e2) {
            e.b(f18420c, "loadSystemCA: exception : " + e2.getMessage());
        }
        e.a(f18420c, "loadSystemCA: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        e.c(f18420c, "checkClientTrusted: ");
        Iterator<X509TrustManager> it = this.f18426a.iterator();
        while (it.hasNext()) {
            try {
                it.next().checkServerTrusted(x509CertificateArr, str);
                return;
            } catch (CertificateException e2) {
                e.b(f18420c, "checkServerTrusted CertificateException" + e2.getMessage());
            }
        }
        throw new CertificateException("checkServerTrusted CertificateException");
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        setChain(x509CertificateArr);
        e.c(f18420c, "checkServerTrusted begin,size=" + x509CertificateArr.length + ",authType=" + str);
        long jCurrentTimeMillis = System.currentTimeMillis();
        int length = x509CertificateArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            X509Certificate x509Certificate = x509CertificateArr[i2];
            e.a(f18420c, "server ca chain: getSubjectDN is :" + x509Certificate.getSubjectDN());
            e.a(f18420c, "IssuerDN :" + x509Certificate.getIssuerDN());
            e.a(f18420c, "SerialNumber : " + x509Certificate.getSerialNumber());
        }
        int size = this.f18426a.size();
        for (int i3 = 0; i3 < size; i3++) {
            try {
                e.c(f18420c, "check server i=" + i3);
                X509TrustManager x509TrustManager = this.f18426a.get(i3);
                X509Certificate[] acceptedIssuers = x509TrustManager.getAcceptedIssuers();
                if (acceptedIssuers != null) {
                    e.c(f18420c, "client root ca size=" + acceptedIssuers.length);
                    for (int i4 = 0; i4 < acceptedIssuers.length; i4++) {
                        e.a(f18420c, "client root ca getIssuerDN :" + acceptedIssuers[i4].getIssuerDN());
                    }
                }
                x509TrustManager.checkServerTrusted(x509CertificateArr, str);
                e.c(f18420c, "checkServerTrusted end, " + x509CertificateArr[x509CertificateArr.length - 1].getIssuerDN());
                return;
            } catch (CertificateException e2) {
                e.b(f18420c, "checkServerTrusted error :" + e2.getMessage() + " , time : " + i3);
                if (i3 == size - 1) {
                    if (x509CertificateArr.length > 0) {
                        e.b(f18420c, "root ca issuer : " + x509CertificateArr[x509CertificateArr.length - 1].getIssuerDN());
                    }
                    throw e2;
                }
            }
        }
        e.a(f18420c, "checkServerTrusted: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        try {
            ArrayList arrayList = new ArrayList();
            Iterator<X509TrustManager> it = this.f18426a.iterator();
            while (it.hasNext()) {
                arrayList.addAll(Arrays.asList(it.next().getAcceptedIssuers()));
            }
            return (X509Certificate[]) arrayList.toArray(new X509Certificate[arrayList.size()]);
        } catch (Exception e2) {
            e.b(f18420c, "getAcceptedIssuers exception : " + e2.getMessage());
            return new X509Certificate[0];
        }
    }

    public X509Certificate[] getChain() {
        return this.f18427b;
    }

    public List<X509TrustManager> getX509TrustManagers() {
        return this.f18426a;
    }

    public void setChain(X509Certificate[] x509CertificateArr) {
        this.f18427b = x509CertificateArr;
    }

    public void setX509TrustManagers(List<X509TrustManager> list) {
        this.f18426a = list;
    }

    public SecureX509TrustManager(Context context, boolean z2) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, IllegalArgumentException {
        this.f18426a = new ArrayList();
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        ContextUtil.setContext(context);
        if (z2) {
            a();
        }
        a(context);
        if (this.f18426a.isEmpty()) {
            throw new CertificateException("X509TrustManager is empty");
        }
    }

    public SecureX509TrustManager(InputStream inputStream, String str) throws IOException, IllegalArgumentException {
        this.f18426a = new ArrayList();
        a(inputStream, str);
    }

    public SecureX509TrustManager(String str) throws IllegalArgumentException, FileNotFoundException {
        this(str, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(android.content.Context r7) throws java.security.NoSuchAlgorithmException, java.io.IOException, java.security.KeyStoreException, java.security.cert.CertificateException {
        /*
            r6 = this;
            java.lang.String r0 = "loadBksCA"
            java.lang.String r1 = "SX509TM"
            com.huawei.secure.android.common.ssl.util.e.c(r1, r0)
            long r2 = java.lang.System.currentTimeMillis()
            java.io.InputStream r0 = com.huawei.secure.android.common.ssl.util.BksUtil.getFilesBksIS(r7)
            if (r0 == 0) goto L3c
            java.lang.String r4 = "get bks not from assets"
            com.huawei.secure.android.common.ssl.util.e.c(r1, r4)     // Catch: java.io.IOException -> L1a java.security.cert.CertificateException -> L1c java.security.KeyStoreException -> L1e java.security.NoSuchAlgorithmException -> L20 java.lang.OutOfMemoryError -> L22
            r6.a(r0)     // Catch: java.io.IOException -> L1a java.security.cert.CertificateException -> L1c java.security.KeyStoreException -> L1e java.security.NoSuchAlgorithmException -> L20 java.lang.OutOfMemoryError -> L22
            goto L3c
        L1a:
            r0 = move-exception
            goto L23
        L1c:
            r0 = move-exception
            goto L23
        L1e:
            r0 = move-exception
            goto L23
        L20:
            r0 = move-exception
            goto L23
        L22:
            r0 = move-exception
        L23:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "loadBksCA: exception : "
            r4.append(r5)
            java.lang.String r0 = r0.getMessage()
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            com.huawei.secure.android.common.ssl.util.e.b(r1, r0)
            goto L3e
        L3c:
            if (r0 != 0) goto L50
        L3e:
            java.lang.String r0 = " get bks from assets "
            com.huawei.secure.android.common.ssl.util.e.c(r1, r0)
            android.content.res.AssetManager r7 = r7.getAssets()
            java.lang.String r0 = "hmsrootcas.bks"
            java.io.InputStream r7 = r7.open(r0)
            r6.a(r7)
        L50:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r0 = "loadBksCA: cost : "
            r7.append(r0)
            long r4 = java.lang.System.currentTimeMillis()
            long r4 = r4 - r2
            r7.append(r4)
            java.lang.String r0 = " ms"
            r7.append(r0)
            java.lang.String r7 = r7.toString()
            com.huawei.secure.android.common.ssl.util.e.a(r1, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.secure.android.common.ssl.SecureX509TrustManager.a(android.content.Context):void");
    }

    public SecureX509TrustManager(String str, boolean z2) throws Throwable {
        FileInputStream fileInputStream;
        this.f18426a = new ArrayList();
        try {
            fileInputStream = new FileInputStream(str);
        } catch (Throwable th) {
            th = th;
            fileInputStream = null;
        }
        try {
            a(fileInputStream, "");
            d.a((InputStream) fileInputStream);
            if (z2) {
                a();
            }
        } catch (Throwable th2) {
            th = th2;
            d.a((InputStream) fileInputStream);
            throw th;
        }
    }

    public SecureX509TrustManager(InputStream inputStream, String str, boolean z2) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, IllegalArgumentException {
        this.f18426a = new ArrayList();
        if (z2) {
            a();
        }
        a(inputStream, str);
    }

    private void a(InputStream inputStream) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(f18423f);
            KeyStore keyStore = KeyStore.getInstance("bks");
            keyStore.load(inputStream, "".toCharArray());
            trustManagerFactory.init(keyStore);
            for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
                if (trustManager instanceof X509TrustManager) {
                    this.f18426a.add((X509TrustManager) trustManager);
                }
            }
        } finally {
            d.a(inputStream);
        }
    }

    private void a(InputStream inputStream, String str) throws IOException {
        if (inputStream != null && str != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            try {
                try {
                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(f18423f);
                    KeyStore keyStore = KeyStore.getInstance("bks");
                    keyStore.load(inputStream, str.toCharArray());
                    trustManagerFactory.init(keyStore);
                    TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                    for (TrustManager trustManager : trustManagers) {
                        if (trustManager instanceof X509TrustManager) {
                            this.f18426a.add((X509TrustManager) trustManager);
                        }
                    }
                    d.a(inputStream);
                } catch (IOException | NegativeArraySizeException | OutOfMemoryError | KeyStoreException | NoSuchAlgorithmException | CertificateException e2) {
                    e.b(f18420c, "loadInputStream: exception : " + e2.getMessage());
                }
                e.a(f18420c, "loadInputStream: cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
                return;
            } finally {
                d.a(inputStream);
            }
        }
        throw new IllegalArgumentException("inputstream or trustPwd is null");
    }
}
